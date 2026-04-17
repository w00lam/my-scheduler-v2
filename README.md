# 일정 관리 시스템 (My Scheduler V2)

> **Spring Boot와 JPA를 활용하여 유저 관리, 일정 관리, 댓글 기능을 제공하는 RESTful 서버입니다.**
> 본 프로젝트는 **Cookie & Session** 기반의 인증 방식을 채택하여 보안성을 강화했습니다.

---

## 프로젝트 개요
- **설명**: 회원가입 및 로그인을 통해 개인별 일정을 관리하고 일정에 댓글을 달 수 있는 커뮤니티형 일정 관리 서비스입니다.
- **핵심 가치**: 세션을 통한 사용자 인증, 데이터 간의 연관 관계(유저-일정-댓글) 설계, 그리고 JPA Auditing을 통한 데이터 이력 관리.

---

## 기술 스택
| 분류 | 기술               |
| :--- |:-----------------|
| **Language** | Java 17          |
| **Framework** | Spring Boot 3.x  |
| **Database** | MySQL            |
| **ORM** | Spring Data JPA  |
| **Auth** | Cookie & Session |
| **API Test** | Postman          |

---

## 데이터베이스 설계 (ERD)

![ERD Diagram](./docs/ERD.png)


## API 명세서

<details>
<summary><strong>일정 API 명세서</strong></summary>

| 기능 | Method | URL | 상태코드 |
|-----|--------|-----|----------|
| 일정 생성 | POST | /api/schedules | 201, 400 |
| 일정 전체 조회 | GET | /api/schedules | 200 |
| 일정 단건 조회 | GET | /api/schedules/{scheduleId} | 200, 404 |
| 일정 수정 | PATCH | /api/schedules/{scheduleId} | 200, 400, 401, 404 |
| 일정 삭제 | DELETE | /api/schedules/{scheduleId} | 204, 401, 404 |

<details>
<summary>일정 생성</summary>

### Request
POST /api/schedules
```json
{
  "title": "title",
  "content": "content",
  "userId": 1
}
```

### Response
201 Created
```json
{
  "id": 1,
  "title": "title",
  "content": "content",
  "userId": 1,
  "createdAt": "2026-04-16T12:00:00",
  "updatedAt": "2026-04-16T12:00:00"
}
```

### Error
400 Bad Request (필수값 누락)

</details>

<details> 
<summary>일정 전체 조회</summary>

### Request
GET /api/schedules

### Response
200 OK
```json
{
  "id": 1,
  "title": "title",
  "content": "content",
  "userId": 1,
  "createdAt": "2026-04-16T12:00:00",
  "updatedAt": "2026-04-16T12:00:00"
}

```

</details>

<details> 
<summary>일정 단건 조회</summary>

### Request
GET /api/schedules/{scheduleId}

### Response
200 OK
```json
{
  "id": 1,
  "title": "title",
  "content": "content",
  "userId": 1,
  "createdAt": "2026-04-16T12:00:00",
  "updatedAt": "2026-04-16T12:00:00"
}
```

### Error
404 Not Found (일정 없음)

</details>

<details> 
<summary>일정 수정</summary>

### Request
PATCH /api/schedules/{scheduleId}
```json
{
  "title": "title",
  "content": "content"
}
```
### Response
200 OK
```json
{
  "id": 1,
  "title": "title",
  "content": "content",
  "updatedAt": "2026-04-16T12:10:00"
}
```

### Error
400 Bad Request (요청값 오류)
401 Unauthorized (로그인 필요)
404 Not Found (일정 없음)

</details>

<details> 
<summary>일정 삭제</summary>

### Request
DELETE /api/schedules/{scheduleId}

### Response
204 No Content

### Error
401 Unauthorized (로그인 필요)
404 Not Found (일정 없음)

</details>
</details>

<details>
<summary><strong>유저 API 명세서</strong></summary>

| 기능 | Method | URL | 상태코드 |
|-----|--------|-----|----------|
| 유저 생성 | POST | /api/users | 201, 400 |
| 유저 전체 조회 | GET | /api/users | 200 |
| 유저 단건 조회 | GET | /api/users/{userId} | 200, 404 |
| 유저 수정 | PUT | /api/users/{userId} | 200, 400, 401, 404 |
| 유저 삭제 | DELETE | /api/users/{userId} | 204, 401, 404 |

<details>
<summary>유저 생성(회원 가입)</summary>

### Request
POST /api/users
```json
{
  "name": "name",
  "email": "email@example.com",
  "password": "password"
}
```
Validation
비밀번호는 8자 이상

### Response
201 Created
```json
{
  "id": 1,
  "name": "name",
  "email": "email@example.com",
  "createdAt": "2026-04-16T12:00:00",
  "updatedAt": "2026-04-16T12:00:00"
}
```

### Error
400 Bad Request (필수값 누락)

</details> 

<details> 
<summary>유저 전체 조회</summary>

### Request
GET /api/users

### Response
200 OK
```json
  {
    "id": 1,
    "name": "name",
    "email": "email@example.com",
    "createdAt": "2026-04-16T12:00:00",
    "updatedAt": "2026-04-16T12:00:00"
  }
```

</details> 

<details> 
<summary>유저 단건 조회</summary>

### Request
GET /api/users/{userId}

### Response
200 OK
```json
{
  "id": 1,
  "username": "string",
  "email": "string",
  "createdAt": "2026-04-16T12:00:00",
  "updatedAt": "2026-04-16T12:00:00"
}
```

### Error
404 Not Found (유저 없음)

</details>

<details> 
<summary>유저 수정</summary>

### Request
PUT /api/users/{userId}
```json
{
  "username": "string",
  "email": "string"
}
```

### Response
200 OK
```json
{
  "id": 1,
  "username": "string",
  "email": "string",
  "updatedAt": "2026-04-16T12:10:00"
}
```

### Error
400 Bad Request (요청값 오류)
401 Unauthorized (로그인 필요)
404 Not Found (유저 없음)

</details> 

<details> 
<summary>유저 삭제</summary>

### Request
DELETE /api/users/{userId}

### Response
204 No Content

### Error
401 Unauthorized (로그인 필요)
404 Not Found (유저 없음)

</details> 
</details>

<details>
<summary><strong>로그인 API 명세서</strong></summary>

| 기능 | Method | URL | 상태코드 |
|-----|--------|-----|----------|
| 회원가입 | POST | /api/auth/signup | 201, 400 |
| 로그인 | POST | /api/auth/login | 200, 400, 401 |
| 로그아웃 | POST | /api/auth/logout | 200 |

<details> 
<summary>로그인</summary>

### Request
POST /api/auth/login
```json
{
  "email": "string",
  "password": "string"
}
```

### Response
200 OK
Behavior
세션 생성
JSESSIONID 쿠키 반환

### Error
400 Bad Request (요청값 오류)
401 Unauthorized (이메일 또는 비밀번호 불일치)

</details> 

<details> 
<summary>로그아웃</summary>

### Request
POST /api/auth/logout

### Response
200 OK
Behavior
세션 무효화

</details>
</details>