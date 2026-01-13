let allTasks = [];
let currentFilter = 'all';

function loadTasks() {
    console.log("Fetching tasks from API...");
    request("/api/tasks", "GET")
        .then(tasks => {
            console.log("Tasks found in DB:", tasks);
            allTasks = tasks || [];
            renderTasks();
        })
        .catch(err => {
            console.error("Failed to load tasks:", err);
            alert("Error loading tasks: " + err.message);
        });
}

function renderTasks() {
    const list = document.getElementById("taskList");
    const noTasksMessage = document.getElementById("noTasksMessage");
    const searchQuery = document.getElementById("searchInput").value.toLowerCase();

    list.innerHTML = "";

    const filtered = allTasks.filter(t => {
        const matchesSearch = t.title.toLowerCase().includes(searchQuery) ||
            (t.description && t.description.toLowerCase().includes(searchQuery));

        let matchesFilter = true;
        if (currentFilter === 'pending') matchesFilter = !t.completed;
        if (currentFilter === 'completed') matchesFilter = t.completed;

        return matchesSearch && matchesFilter;
    });

    if (filtered.length === 0) {
        noTasksMessage.style.display = "block";
    } else {
        noTasksMessage.style.display = "none";
        filtered.forEach(t => {
            const li = document.createElement("li");
            li.className = `task-item ${t.completed ? 'completed' : ''}`;

            li.innerHTML = `
                <div class="task-info">
                    <h3>${t.title}</h3>
                    <p>${t.description || 'No description'}</p>
                </div>
                <div class="task-actions">
                    <button class="btn-icon btn-success" onclick="toggleTask(${t.id}, ${t.completed})" title="${t.completed ? 'Mark as Pending' : 'Mark as Completed'}">
                        ${t.completed ? '✓' : '○'}
                    </button>
                    <button class="btn-icon btn-warning" onclick="editTask(${t.id})" title="Edit Task">✎</button>
                    <button class="btn-icon btn-danger" onclick="deleteTask(${t.id})" title="Delete Task">✕</button>
                </div>
            `;
            list.appendChild(li);
        });
    }
}

function createTask() {
    const titleInput = document.getElementById("taskTitle");
    const descInput = document.getElementById("taskDescription");

    if (!titleInput.value.trim()) {
        alert("Please enter a title");
        return;
    }

    request("/api/tasks", "POST", {
        title: titleInput.value,
        description: descInput.value,
        completed: false
    })
        .then(() => {
            titleInput.value = "";
            descInput.value = "";
            loadTasks();
        })
        .catch(err => alert("Error creating task: " + err.message));
}

function toggleTask(id, currentStatus) {
    const task = allTasks.find(t => t.id === id);
    if (!task) return;

    request(`/api/tasks/${id}`, "PUT", {
        title: task.title,
        description: task.description,
        completed: !currentStatus,
        creationDate: task.creationDate // Keep the date as received
    })
        .then(() => loadTasks())
        .catch(err => alert("Error updating task: " + err.message));
}

function deleteTask(id) {
    if (!confirm("Are you sure you want to delete this task?")) return;

    request(`/api/tasks/${id}`, "DELETE")
        .then(() => loadTasks())
        .catch(err => alert("Error deleting task: " + err.message));
}

function editTask(id) {
    const task = allTasks.find(t => t.id === id);
    if (!task) return;

    const newTitle = prompt("New title:", task.title);
    if (newTitle === null) return;

    const newDesc = prompt("New description:", task.description);
    if (newDesc === null) return;

    request(`/api/tasks/${id}`, "PUT", {
        title: newTitle,
        description: newDesc,
        completed: task.completed,
        creationDate: task.creationDate
    })
        .then(() => loadTasks())
        .catch(err => alert("Error updating task: " + err.message));
}

function setFilter(filter) {
    currentFilter = filter;
    document.querySelectorAll('.filter-btn').forEach(btn => btn.classList.remove('active'));
    document.getElementById(`filter-${filter}`).classList.add('active');
    renderTasks();
}

function filterTasks() {
    renderTasks();
}

loadTasks();
