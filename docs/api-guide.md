<div id="header">

# Device Registry Service API Guide

<div id="toc" class="toc2">

<div id="toctitle">

Table of Contents

</div>

- [1. Overview](#_overview)
- [2. Devices](#_devices)
  - [2.1. Create Device](#_create_device)
  - [2.2. Get Device by ID](#_get_device_by_id)
  - [2.3. Get All Devices](#_get_all_devices)
  - [2.4. Filter Devices by Brand](#_filter_devices_by_brand)
  - [2.5. Filter Devices by State](#_filter_devices_by_state)
  - [2.6. Update Device](#_update_device)
  - [2.7. Patch Device](#_patch_device)
  - [2.8. Delete Device](#_delete_device)

</div>

</div>

<div id="content">

<div class="sect1">

## 1. Overview

<div class="sectionbody">

<div class="paragraph">

REST API for managing devices. All endpoints consume and produce
`application/json`.

</div>

</div>

</div>

<div class="sect1">

## 2. Devices

<div class="sectionbody">

<div class="sect2">

### 2.1. Create Device

<div class="paragraph">

`POST /devices`

</div>

| Path    | Type     | Description                                  |
|---------|----------|----------------------------------------------|
| `name`  | `String` | Device name                                  |
| `brand` | `String` | Device brand                                 |
| `state` | `String` | Device state: AVAILABLE, IN_USE, or INACTIVE |

Table 1. Request Fields {.tableblock .frame-all .grid-all .stretch}

<div class="listingblock">

<div class="title">

Example Request

</div>

<div class="content">

``` highlightjs
POST /devices HTTP/1.1
Content-Type: application/json
Content-Length: 55
Host: localhost:8080

{"name":"Phone X","brand":"Apple","state":"AVAILABLE"}
```

</div>

</div>

| Path           | Type     | Description                              |
|----------------|----------|------------------------------------------|
| `id`           | `Number` | Unique device identifier                 |
| `name`         | `String` | Device name                              |
| `brand`        | `String` | Device brand                             |
| `state`        | `String` | Device state                             |
| `creationTime` | `String` | Timestamp when the device was registered |

Table 2. Response Fields {.tableblock .frame-all .grid-all .stretch}

<div class="listingblock">

<div class="title">

Example Response

</div>

<div class="content">

``` highlightjs
HTTP/1.1 201 Created
Content-Type: application/json
Content-Length: 99

{"id":1,"name":"Phone X","brand":"Apple","state":"AVAILABLE","creationTime":"2026-01-01T00:00:00Z"}
```

</div>

</div>

------------------------------------------------------------------------

</div>

<div class="sect2">

### 2.2. Get Device by ID

<div class="paragraph">

`GET /devices/{id}`

</div>

| Parameter | Description |
|-----------|-------------|
| `id`      | Device ID   |

Table 3. /devices/{id} {.tableblock .frame-all .grid-all .stretch}

| Path           | Type     | Description                              |
|----------------|----------|------------------------------------------|
| `id`           | `Number` | Unique device identifier                 |
| `name`         | `String` | Device name                              |
| `brand`        | `String` | Device brand                             |
| `state`        | `String` | Device state                             |
| `creationTime` | `String` | Timestamp when the device was registered |

Table 4. Response Fields {.tableblock .frame-all .grid-all .stretch}

<div class="listingblock">

<div class="title">

Example Request

</div>

<div class="content">

``` highlightjs
GET /devices/1 HTTP/1.1
Host: localhost:8080
```

</div>

</div>

<div class="listingblock">

<div class="title">

Example Response

</div>

<div class="content">

``` highlightjs
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 99

{"id":1,"name":"Phone X","brand":"Apple","state":"AVAILABLE","creationTime":"2026-01-01T00:00:00Z"}
```

</div>

</div>

------------------------------------------------------------------------

</div>

<div class="sect2">

### 2.3. Get All Devices

<div class="paragraph">

`GET /devices`

</div>

| Path              | Type     | Description                              |
|-------------------|----------|------------------------------------------|
| `[].id`           | `Number` | Unique device identifier                 |
| `[].name`         | `String` | Device name                              |
| `[].brand`        | `String` | Device brand                             |
| `[].state`        | `String` | Device state                             |
| `[].creationTime` | `String` | Timestamp when the device was registered |

Table 5. Response Fields {.tableblock .frame-all .grid-all .stretch}

<div class="listingblock">

<div class="title">

Example Request

</div>

<div class="content">

``` highlightjs
GET /devices HTTP/1.1
Host: localhost:8080
```

</div>

</div>

<div class="listingblock">

<div class="title">

Example Response

</div>

<div class="content">

``` highlightjs
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 201

[{"id":1,"name":"Phone X","brand":"Apple","state":"AVAILABLE","creationTime":"2026-01-01T00:00:00Z"},{"id":2,"name":"Phone X","brand":"Apple","state":"AVAILABLE","creationTime":"2026-01-01T00:00:00Z"}]
```

</div>

</div>

------------------------------------------------------------------------

</div>

<div class="sect2">

### 2.4. Filter Devices by Brand

<div class="paragraph">

`GET /devices?brand={brand}`

</div>

| Parameter | Description             |
|-----------|-------------------------|
| `brand`   | Filter devices by brand |

Table 6. Query Parameters {.tableblock .frame-all .grid-all .stretch}

<div class="listingblock">

<div class="title">

Example Request

</div>

<div class="content">

``` highlightjs
GET /devices?brand=Apple HTTP/1.1
Host: localhost:8080
```

</div>

</div>

<div class="listingblock">

<div class="title">

Example Response

</div>

<div class="content">

``` highlightjs
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 101

[{"id":1,"name":"Phone X","brand":"Apple","state":"AVAILABLE","creationTime":"2026-01-01T00:00:00Z"}]
```

</div>

</div>

------------------------------------------------------------------------

</div>

<div class="sect2">

### 2.5. Filter Devices by State

<div class="paragraph">

`GET /devices?state={state}`

</div>

| Parameter | Description                                             |
|-----------|---------------------------------------------------------|
| `state`   | Filter devices by state: AVAILABLE, IN_USE, or INACTIVE |

Table 7. Query Parameters {.tableblock .frame-all .grid-all .stretch}

<div class="listingblock">

<div class="title">

Example Request

</div>

<div class="content">

``` highlightjs
GET /devices?state=AVAILABLE HTTP/1.1
Host: localhost:8080
```

</div>

</div>

<div class="listingblock">

<div class="title">

Example Response

</div>

<div class="content">

``` highlightjs
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 101

[{"id":1,"name":"Phone X","brand":"Apple","state":"AVAILABLE","creationTime":"2026-01-01T00:00:00Z"}]
```

</div>

</div>

------------------------------------------------------------------------

</div>

<div class="sect2">

### 2.6. Update Device

<div class="paragraph">

`PUT /devices/{id}`

</div>

| Parameter | Description |
|-----------|-------------|
| `id`      | Device ID   |

Table 8. /devices/{id} {.tableblock .frame-all .grid-all .stretch}

| Path    | Type     | Description                                  |
|---------|----------|----------------------------------------------|
| `name`  | `String` | Device name                                  |
| `brand` | `String` | Device brand                                 |
| `state` | `String` | Device state: AVAILABLE, IN_USE, or INACTIVE |

Table 9. Request Fields {.tableblock .frame-all .grid-all .stretch}

<div class="listingblock">

<div class="title">

Example Request

</div>

<div class="content">

``` highlightjs
PUT /devices/1 HTTP/1.1
Content-Type: application/json
Content-Length: 52
Host: localhost:8080

{"name":"Phone X","brand":"Apple","state":"IN_USE"}
```

</div>

</div>

| Path           | Type     | Description                              |
|----------------|----------|------------------------------------------|
| `id`           | `Number` | Unique device identifier                 |
| `name`         | `String` | Device name                              |
| `brand`        | `String` | Device brand                             |
| `state`        | `String` | Device state                             |
| `creationTime` | `String` | Timestamp when the device was registered |

Table 10. Response Fields {.tableblock .frame-all .grid-all .stretch}

<div class="listingblock">

<div class="title">

Example Response

</div>

<div class="content">

``` highlightjs
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 96

{"id":1,"name":"Phone X","brand":"Apple","state":"IN_USE","creationTime":"2026-01-01T00:00:00Z"}
```

</div>

</div>

------------------------------------------------------------------------

</div>

<div class="sect2">

### 2.7. Patch Device

<div class="paragraph">

`PATCH /devices/{id}`

</div>

| Parameter | Description |
|-----------|-------------|
| `id`      | Device ID   |

Table 11. /devices/{id} {.tableblock .frame-all .grid-all .stretch}

| Path | Type | Description |
|----|----|----|
| `name` | `class java.lang.String` | Device name (optional) |
| `brand` | `class java.lang.String` | Device brand (optional) |
| `state` | `String` | Device state (optional): AVAILABLE, IN_USE, or INACTIVE |

Table 12. Request Fields {.tableblock .frame-all .grid-all .stretch}

<div class="listingblock">

<div class="title">

Example Request

</div>

<div class="content">

``` highlightjs
PATCH /devices/1 HTTP/1.1
Content-Type: application/json
Content-Length: 21
Host: localhost:8080

{"state":"INACTIVE"}
```

</div>

</div>

| Path           | Type     | Description                              |
|----------------|----------|------------------------------------------|
| `id`           | `Number` | Unique device identifier                 |
| `name`         | `String` | Device name                              |
| `brand`        | `String` | Device brand                             |
| `state`        | `String` | Device state                             |
| `creationTime` | `String` | Timestamp when the device was registered |

Table 13. Response Fields {.tableblock .frame-all .grid-all .stretch}

<div class="listingblock">

<div class="title">

Example Response

</div>

<div class="content">

``` highlightjs
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 98

{"id":1,"name":"Phone X","brand":"Apple","state":"INACTIVE","creationTime":"2026-01-01T00:00:00Z"}
```

</div>

</div>

------------------------------------------------------------------------

</div>

<div class="sect2">

### 2.8. Delete Device

<div class="paragraph">

`DELETE /devices/{id}`

</div>

| Parameter | Description |
|-----------|-------------|
| `id`      | Device ID   |

Table 14. /devices/{id} {.tableblock .frame-all .grid-all .stretch}

<div class="listingblock">

<div class="title">

Example Request

</div>

<div class="content">

``` highlightjs
DELETE /devices/1 HTTP/1.1
Host: localhost:8080
```

</div>

</div>

<div class="listingblock">

<div class="title">

Example Response

</div>

<div class="content">

``` highlightjs
HTTP/1.1 204 No Content
```

</div>

</div>

</div>

</div>

</div>

</div>

<div id="footer">

<div id="footer-text">

Last updated 2026-04-10 19:10:15 +0300

</div>

</div>
