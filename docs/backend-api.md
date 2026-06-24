# Backend API and Data Ownership

## Configuration and Imports

`backend/NSync/settings.py` imports environment values through `python-dotenv`, Django settings utilities, REST Framework, Simple JWT, and CORS configuration. It reads database configuration from `.env` and connects Django to PostgreSQL. `backend/docker-compose.yml` starts the PostgreSQL service using the same database variables.

Important setup files:

- `.env.example`: placeholder environment variables. Copy it to `.env` locally.
- `requirements.txt`: Django, DRF, Simple JWT, PostgreSQL driver, CORS, dotenv, and Gunicorn dependencies.
- `NSync/urls.py`: mounts the `core.api_urls` paths below `/api/`.

## Models

`core/models.py` imports `django.db.models` and Django's configured `User` model. Its models are:

- `UserProfile`: one-to-one extension of the authenticated user with display name.
- `Note`: user-owned title, content, tag, and timestamps.
- `Flashcard`: a question/answer linked to a `Note`, with difficulty, mastery level, and review count.
- `QuizAttempt`: one recorded answer/outcome for a user and flashcard.
- `UserProgress`: one progress row per user with XP, level, streak, accuracy, review count, and last-review date.

The legacy `User` model remains in the codebase but the authenticated API uses Django's configured user model and `UserProfile`.

## Serializers

`core/serializers.py` imports DRF `serializers`, Django authentication helpers, the configured `User` model, and local models.

- `NoteSerializer`, `FlashcardSerializer`, `QuizAttemptSerializer`, and `UserProgressSerializer` convert database records to JSON and validate request bodies.
- `AuthenticatedUserSerializer` exposes the authenticated user's ID, email, and display name.
- `RegisterSerializer.validate_email` rejects existing accounts.
- `RegisterSerializer.create` creates the Django user and linked `UserProfile`.
- `LoginSerializer.validate` authenticates email/password and exposes the validated user.

## API Views and Functions

`core/api_views.py` imports DRF decorators, `Response`, status codes, permissions, viewsets, Django query helpers, Simple JWT blacklist/token classes, serializers, and models.

| Callable | Purpose |
| --- | --- |
| `build_auth_response(user)` | Creates JWT access/refresh tokens and serializes the user. |
| `health_check` | Public health response. |
| `NoteViewSet.get_queryset` | Returns only notes owned by `request.user`, ordered by update time. |
| `NoteViewSet.perform_create` | Sets `owner=request.user` when creating a note. |
| `FlashcardViewSet.get_queryset` | Returns only cards connected to the current user's notes and includes connected note data. |
| `FlashcardViewSet.perform_create` | Validates that the selected note belongs to the current user before saving. |
| `progress_detail` | Gets or creates the current user's progress row. |
| `complete_review` | Saves answer attempts, updates card mastery, XP, accuracy, level, and streak, then returns updated progress. |
| `calculate_level` | Converts total XP into the current level. |
| `register` | Validates a registration request, creates the account, and returns tokens. |
| `login` | Validates credentials and returns tokens. |
| `logout` | Blacklists a refresh token. |
| `me` | Returns the current authenticated user. |

## Routes

`core/api_urls.py` imports `path`, `include`, `DefaultRouter`, `TokenRefreshView`, and `api_views`.

| Method / route | Auth | Effect |
| --- | --- | --- |
| `GET /api/health/` | No | Health response. |
| `POST /api/auth/register/` | No | Create account and return access/refresh tokens. |
| `POST /api/auth/login/` | No | Return access/refresh tokens. |
| `POST /api/auth/refresh/` | No | Exchange a refresh token for new tokens. |
| `POST /api/auth/logout/` | Access token | Blacklist supplied refresh token. |
| `GET /api/auth/me/` | Access token | Return authenticated user data. |
| `GET, POST /api/notes/` | Access token | List/create the user's notes. |
| `GET, PUT, DELETE /api/notes/{id}/` | Access token | Read/update/delete one owned note. |
| `GET, POST /api/flashcards/` | Access token | List/create cards for the user's notes. |
| `GET, PUT, DELETE /api/flashcards/{id}/` | Access token | Read/update/delete one owned card. |
| `GET /api/progress/` | Access token | Return/create the user's progress. |
| `POST /api/review/complete/` | Access token | Persist review answers and return new progress. |

## Tests

`core/tests.py` imports `APITestCase`, Django `User`, and project models. `AuthenticationTests` verifies registration and invalid login. `LearningDataOwnershipTests` creates two users and verifies authentication requirements, note isolation, flashcard ownership validation, and independent progress.

Run them from `backend/`:

```bash
python manage.py test core
```
