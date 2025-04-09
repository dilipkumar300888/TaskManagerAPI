### API Endpoints

| Method | Endpoint                        | Description                                             |
|--------|---------------------------------|---------------------------------------------------------|
| POST   | `/api/v1/tasks`                 | Create new task                                         |
| GET    | `/api/v1/tasks`                 | Get all tasks                                           |
| GET    | `/api/v1/tasks?completed=false` | Get all tasks - optional filtering on 'completed' param |
| GET    | `/api/v1/tasks/{id}`            | Get single task                                         |
| PUT    | `/api/v1/tasks/{id}`            | Full task update                                        |
| DELETE | `/api/v1/tasks/{id}`            | Delete task                                             |

### Examples with bash/powershell

```bash
curl -X POST http://localhost:8080/api/v1/tasks \
-H "Content-Type: application/json" \
-d '{"title":"New Task","description":"Lorem ip sum",completed":false}'
```

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/v1/tasks" -Method Post `
-Headers @{"Content-Type"="application/json"} `
-Body '{"title":"PS Task","description":"Lorem ip sum","completed":false}'
```