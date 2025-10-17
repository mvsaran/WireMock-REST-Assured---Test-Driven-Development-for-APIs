# 🎭 WireMock REST Assured - Test-Driven Development for APIs

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![REST Assured](https://img.shields.io/badge/REST_Assured-00A99D?style=for-the-badge)
![WireMock](https://img.shields.io/badge/WireMock-FF6B6B?style=for-the-badge)

**🚀 Enabling True Test-Driven Development for REST APIs**

</div>

---

## 📋 Project Overview

This project demonstrates **Test-Driven Development (TDD)** for REST API testing using **WireMock** as a mock server and **REST Assured** for API test automation. The key advantage is that QA teams can develop and validate test cases even before the backend APIs are fully developed, enabling true parallel development.

### 🎯 Key Benefits

- ✨ **Early Test Development**: Write tests before backend APIs are ready
- 🔄 **Parallel Development**: QA and Dev teams work independently
- 🎯 **Easy Transition**: Switch from mock to real APIs seamlessly
- 📐 **Consistent Testing**: Maintain same test structure throughout SDLC
- ⚡ **Fast Feedback**: No dependency on backend availability
- 🛡️ **Isolated Testing**: No external dependencies or database requirements

---

## 🏗️ Project Structure

```
WireMockRESTAssured/
│
├── 📂 src/main/java/              # Main source (if needed)
├── 📂 src/main/resources/         # Main resources
│
├── 📂 src/test/java/
│   └── 📦 com/demo/
│       ├── 📄 BaseTest.java           # Base setup with WireMock server
│       ├── 📄 CrudApiTest.java        # CRUD operation tests
│       └── 📄 TestDataLoader.java     # JSON test data loader utility
│
└── 📂 src/test/resources/
    └── 📁 testdata/
        ├── 📋 user_get.json           # Mock response for GET user
        ├── 📋 user_post.json          # Mock request/response for POST
        ├── 📋 user_put.json           # Mock request/response for PUT
        └── 📋 user_delete.json        # Mock response for DELETE
```

---

## 🔧 Technologies Used

| Technology | Version | Purpose | Badge |
|------------|---------|---------|-------|
| **WireMock** | Latest | Mock HTTP server for simulating APIs | 🎭 |
| **REST Assured** | Latest | REST API testing framework | 🔌 |
| **JUnit 5** | Jupiter | Test execution framework | ✅ |
| **Hamcrest** | Latest | Assertion matchers | 🎯 |
| **Java** | 8+ | Programming language | ☕ |
| **Maven** | Latest | Build and dependency management | 📦 |

---

## 📦 Maven Dependencies

```xml
<dependencies>
    <!-- WireMock for mocking HTTP services -->
    <dependency>
        <groupId>com.github.tomakehurst</groupId>
        <artifactId>wiremock</artifactId>
        <version>2.35.0</version>
        <scope>test</scope>
    </dependency>
    
    <!-- REST Assured for API testing -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.3.0</version>
        <scope>test</scope>
    </dependency>
    
    <!-- JUnit 5 for test execution -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.9.0</version>
        <scope>test</scope>
    </dependency>
    
    <!-- Hamcrest for assertions -->
    <dependency>
        <groupId>org.hamcrest</groupId>
        <artifactId>hamcrest</artifactId>
        <version>2.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 🚀 Getting Started

### ✅ Prerequisites

- ☕ Java 8 or higher installed
- 📦 Maven installed and configured
- 💻 IDE (Eclipse/IntelliJ IDEA) with JUnit support

### 📥 Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd WireMockRESTAssured
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Verify setup**
   ```bash
   mvn test
   ```

---

## 📝 Implementation Details

### 1️⃣ BaseTest.java - Foundation Class

The `BaseTest` class initializes the WireMock server and provides base URL configuration.

**🔑 Key Features:**
- 🚀 Starts WireMock server on port 8080 (or random port)
- 🔗 Sets up REST Assured base URI
- 🔄 Provides lifecycle management (`@BeforeAll`, `@AfterAll`)
- ⚙️ Centralized server configuration

**Code Highlights:**
```java
@BeforeAll
static void setup() {
    // Start WireMock server on port 8080
    wireMockServer = new WireMockServer(
        WireMockConfiguration.options().port(8080)
    );
    wireMockServer.start();
    
    // Configure REST Assured base URL
    RestAssured.baseURI = "http://localhost:8080";
}

@AfterAll
static void tearDown() {
    // Stop WireMock server after all tests
    if (wireMockServer != null && wireMockServer.isRunning()) {
        wireMockServer.stop();
    }
}
```

---

### 2️⃣ CrudApiTest.java - API Test Cases

Contains all CRUD operation tests using REST Assured with WireMock stubs.

**📋 Test Cases Implemented:**

#### 🟢 GET - Retrieve User
```java
@Test
@DisplayName("GET - Retrieve User")
public void testGetUser() {
    String response = TestDataLoader.loadJson("user_get.json");
    
    stubFor(get(urlEqualTo("/api/users/1"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(response)));
    
    given()
        .contentType(ContentType.JSON)
    .when()
        .get("/api/users/1")
    .then()
        .statusCode(200)
        .body("id", equalTo("1"))
        .body("name", equalTo("John Doe"));
}
```

#### 🔵 POST - Create User
```java
@Test
@DisplayName("POST - Create User")
public void testPostUser() {
    String response = TestDataLoader.loadJson("user_post.json");
    
    stubFor(post(urlEqualTo("/api/users"))
        .willReturn(aResponse()
            .withStatus(201)
            .withHeader("Content-Type", "application/json")
            .withBody(response)));
    
    given()
        .contentType(ContentType.JSON)
        .body(response)
    .when()
        .post("/api/users")
    .then()
        .statusCode(201)
        .body("name", equalTo("Jane Smith"));
}
```

#### 🟡 PUT - Update User
```java
@Test
@DisplayName("PUT - Update User")
public void testPutUser() {
    String response = TestDataLoader.loadJson("user_put.json");
    
    stubFor(put(urlEqualTo("/api/users/1"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(response)));
    
    given()
        .contentType(ContentType.JSON)
        .body(response)
    .when()
        .put("/api/users/1")
    .then()
        .statusCode(200)
        .body("name", equalTo("John Updated"));
}
```

#### 🔴 DELETE - Remove User
```java
@Test
@DisplayName("DELETE - Remove User")
public void testDeleteUser() {
    stubFor(delete(urlEqualTo("/api/users/1"))
        .willReturn(aResponse()
            .withStatus(204)));
    
    given()
    .when()
        .delete("/api/users/1")
    .then()
        .statusCode(204);
}
```

---

### 3️⃣ TestDataLoader.java - Utility Class

Helper class to load JSON test data from the resources folder.

**🎯 Features:**
- 📖 Reads JSON files from `src/test/resources/testdata/`
- 📤 Returns JSON content as String
- 🛡️ Handles file I/O exceptions
- 🗂️ Centralized data management

---

### 4️⃣ Test Data Files

#### user_get.json
```json
{
  "id": "1",
  "name": "John Doe",
  "email": "john.doe@example.com",
  "role": "Admin"
}
```

#### user_post.json
```json
{
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "role": "User"
}
```

#### user_put.json
```json
{
  "id": "1",
  "name": "John Updated",
  "email": "john.updated@example.com",
  "role": "SuperAdmin"
}
```

#### user_delete.json
```json
{
  "message": "User deleted successfully"
}
```

---

## ▶️ Running Tests

### 🏃 Run all tests
```bash
mvn test
```

### 🎯 Run specific test class
```bash
mvn test -Dtest=CrudApiTest
```

### 🔍 Run specific test method
```bash
mvn test -Dtest=CrudApiTest#testGetUser
```

### 📊 Generate test reports
```bash
mvn surefire-report:report
```

---

## 🔄 Transitioning from Mock to Real APIs

When backend APIs are ready, follow these simple steps:

### 🔧 Step 1: Update Base URL
```java
// In BaseTest.java - Comment out WireMock setup
// @BeforeAll
// static void setup() {
//     wireMockServer = new WireMockServer(...);
//     wireMockServer.start();
// }

// Update base URI to real backend
RestAssured.baseURI = "https://api.production.com";
```

### Step 2: Remove Stub Definitions
```java
// Remove WireMock stubFor() calls from test methods
// The actual API will now respond

@Test
public void testGetUser() {
    // stubFor() removed - real API call
    given()
        .contentType(ContentType.JSON)
    .when()
        .get("/api/users/1")
    .then()
        .statusCode(200)
        .body("id", equalTo("1"));
}
```

### Step 3: Update Assertions (if needed)
- Adjust expected responses based on real API behavior
- Update status codes if they differ
- Modify response body validations as needed

---

## 🧪 Test Execution Results

After running tests, you should see:

```
Runs: 4/4
Errors: 0
Failures: 0

✓ PUT - Update User (3.050 s)
✓ GET - Retrieve User (0.097 s)
✓ DELETE - Remove User (0.070 s)
✓ POST - Create User (0.089 s)

✓ WireMock started on: http://localhost:8080
✓ PUT Test Passed
✓ GET Test Passed
✓ DELETE Test Passed
✓ POST Test Passed
✓ WireMock stopped.
```

---

## 🎯 Best Practices Implemented

1. **Separation of Concerns**: Base setup, test data, and tests are in separate files
2. **Reusable Components**: TestDataLoader can be used across multiple test classes
3. **Clear Naming**: Test methods use descriptive names with `@DisplayName`
4. **Resource Management**: WireMock server properly started and stopped
5. **Data-Driven**: Test data externalized in JSON files
6. **Maintainable**: Easy to add new tests or modify existing ones

---

## 📊 Advanced Features (Future Enhancements)

- [ ] Add request/response logging
- [ ] Implement parameterized tests
- [ ] Add schema validation
- [ ] Integrate with CI/CD pipeline
- [ ] Add performance testing metrics
- [ ] Implement test data factories
- [ ] Add authentication/authorization tests
- [ ] Create HTML test reports

---

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

---

## 🙏 Acknowledgments

- WireMock for excellent mocking capabilities
- REST Assured for fluent API testing
- JUnit 5 for modern testing framework

---

## Author

Saran Kumar

**Happy Testing! 🚀**
