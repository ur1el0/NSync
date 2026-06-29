# Authentication and Data Flow

## Registration

The Register screen sends the user's display name, email, and password to `POST /api/auth/register/`. Django validates the input, creates the user and profile, then returns the user details with JWT access and refresh tokens.

## Login

The Login screen sends the email and password to `POST /api/auth/login/`. Django authenticates the credentials and returns an access token, refresh token, and the authenticated user's information.

## Token Storage

`AuthRepository` converts the authentication response into an `AuthSession`. `AuthSessionStore` saves the tokens, user ID, display name, and email locally using Android DataStore Preferences.

## Access Token

The access token proves that the user is authenticated when requesting protected API data. It expires after a short period to reduce the risk of a stolen token being used for a long time.

## Refresh Token

The refresh token is used by `AuthRepository` to request a new access token from `POST /api/auth/refresh/`. It lasts longer than the access token and prevents the user from entering their password every time the access token expires.

## Authenticated Requests

`AuthInterceptor` reads the stored access token and adds `Authorization: Bearer <access_token>` to protected OkHttp requests. Public endpoints such as health, registration, login, and token refresh are sent without this header.

## Restoring a Session

When the app starts, `AuthViewModel` asks `AuthRepository` to restore and verify the saved session through `GET /api/auth/me/`. If the access token receives a `401`, the repository attempts one token refresh and retries the request; if that fails, the local session is cleared.

## Logout

Logout sends the refresh token to `POST /api/auth/logout/`, where Django blacklists it. The app then clears the saved session so protected screens can no longer use the previous credentials.

## Sending Data from the App

A Compose screen calls a ViewModel action, and the ViewModel calls the appropriate repository function. The repository creates a request DTO, while Retrofit and Gson convert it into JSON and send it through the endpoint declared in `ApiService`.

## Receiving Data in Django

Django routes the request from `api_urls.py` to a function or ViewSet in `api_views.py`. A serializer validates the JSON, the view reads or changes Django models, and PostgreSQL stores the resulting user, note, flashcard, review, or progress data.

## Returning Data to the App

Django serializers convert model records into a JSON response. Retrofit and Gson convert the response into DTOs, the repository maps those DTOs into app models, and the ViewModel updates its UI state.

## Updating the Interface

Compose observes the ViewModel's state and automatically recomposes when that state changes. This displays current backend data without the screen directly communicating with Retrofit or PostgreSQL.

## User Data Isolation

The backend filters notes using `owner=request.user` and flashcards using their connected note's owner. This ensures an authenticated user can only retrieve or modify their own learning data.

## Emulator Connection

Retrofit uses `http://127.0.0.1:8000/` as the development server address. `adb reverse tcp:8000 tcp:8000` forwards the emulator's port 8000 to the Django server running on the development computer.

## Complete Flow

The complete path is: Compose screen -> ViewModel -> repository -> Retrofit/OkHttp -> Django API -> serializer -> model -> PostgreSQL. The response returns through the same layers in reverse until Compose displays the updated state.
