# Future Improvements

## Observability

- **Metrics** — Add Micrometer + Prometheus integration. Expose a `/actuator/metrics` endpoint

## API

- **Pagination** — `GET /devices` should support `page` and `size` query parameters. Returning unbounded lists will not scale.
- **Error response body** — Standardize error responses with a consistent error structure
- **Combined filters** — `GET /devices` currently supports only `brand` or `state`, not both at once