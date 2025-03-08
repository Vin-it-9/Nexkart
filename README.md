# Nexkart E-Commerce Website

### Project Description:

Developed a feature-rich **E-Commerce WebApp** for *Nexkart*, with comprehensive administrative capabilities and user-friendly shopping experience. The system leverages **Spring Boot**, **Spring Security**, **Spring Data JPA**, and **Spring Web** for backend operations, with a dynamic frontend using **Thymeleaf**, **Tailwind CSS**, **JavaScript**, and **jQuery**. Data is stored in **AWS RDS MySQL**, while product images are managed through **AWS S3 buckets**. The platform ensures secure authentication through both traditional methods and social login integrations, with a smooth checkout process via PayPal integration.

### Key Features:

- **Admin Panel**:
  - **User Management:** Manage user accounts with differentiated roles (Admin, Salesperson, Shipper, Editor, Assistant).
  - **Category Management:** Create, edit, and organize product categories and subcategories.
  - **Brand Management:** Maintain brand information and associations.
  - **Product Management:** Comprehensive product creation and editing with AWS S3 image uploads.
  - **Order Management:** Track, process, and update customer orders.
  - **Shipping Management:** Configure shipping options and track delivery status.
  - **Reports and Analytics:** Generate sales reports, inventory analyses, and user engagement metrics.
  - **Website Settings:** Configure site appearance, payment options, and operational parameters.

- **User Panel**:
  - **Account Management:** Register, login, and manage personal information.
  - **Social Authentication:** Sign up and login via Google and Facebook OAuth.
  - **Product Browsing:** Search, filter, and sort products with pagination support.
  - **Shopping Cart:** Add items, adjust quantities, and manage selections.
  - **Product Reviews:** Leave ratings and feedback on purchased items.
  - **Checkout Process:** Secure payment processing via PayPal integration and Cash on delivery
  - **Order Tracking:** View order history and track shipping status.
  - **Responsive Design:** Optimized shopping experience across desktop and mobile devices.

### Technologies Used:
- **Backend**: Spring Boot, Spring Data JPA, Spring Security, Spring Web, Spring Mail
- **Frontend**: Thymeleaf, Tailwind CSS, JavaScript, jQuery
- **Database**: AWS RDS MySQL
- **Storage**: AWS S3 Bucket (for product images)
- **Authentication**: Spring Security, Google OAuth, Facebook OAuth
- **Payment Processing**: PayPal API Integration
- **Deployment**: Heroku Platform
- **Build Tool**: Maven

This comprehensive e-commerce solution provides both administrators and customers with intuitive interfaces for their respective needs, ensuring a smooth shopping experience while maintaining robust backend management capabilities.

## Project Architecture

Nexkart consists of two separate applications:
- **Admin Portal**: Runs on port 8080 and accessed via `/NexkartAdmin` path
- **User Portal**: Runs on port 8081 and accessed via `/Nexkart` path

This separation provides better security and resource management between administrative functions and user-facing features.

## Prerequisites

Before getting started, ensure you have the following installed on your machine:

- **Java 19**: Required to run the application.
- **Maven 3.8.1 or higher**: Used for building and managing dependencies.
- **MySQL 8.0 or higher**: For local development (production uses AWS RDS).
- **IntelliJ IDEA**: Recommended IDE for running and debugging the project.
- **AWS Account**: For accessing RDS and S3 services (if working with production resources).
- **Heroku Account**: For accessing on web (if you want to deploy project).

## Getting Started

### 1. Clone or Download the Repository

Start by cloning the project repository to your local machine:

```bash
git clone https://github.com/Vin-it-9/Nexkart.git
```

To download the repository, visit the GitHub Repository, click on the Code button, and select Download ZIP.

### 2. Open the Project in IntelliJ IDEA

To work with the Spring Boot project in IntelliJ IDEA, follow these steps:

1. **Download and Install IntelliJ IDEA**:
   - Download and install [IntelliJ IDEA](https://www.jetbrains.com/idea/).

2. **Clone the Project Repository**:
   - Clone the project repository by running the following command in your terminal:
     ```bash
     git clone https://github.com/Vin-it-9/Nexkart.git
     ```

3. **Install JDK**:
   - Ensure you have [JDK 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html) installed on your machine.

4. **Install the Spring Boot Runner Plugin**:
   - Open IntelliJ IDEA and navigate to **File > Settings** (or **IntelliJ IDEA > Preferences** on macOS).
   - Go to **Plugins** and search for **Spring Boot**.
   - Install the **Spring Boot** plugin and restart IntelliJ IDEA if prompted.

5. **Open the Projects**:
   - The repository contains two Spring Boot projects:
     - Admin Portal: Located in the `/NexkartBackend` directory
     - User Portal: Located in the `/NexkartFrontend` directory
   - Open each project separately in IntelliJ by selecting **File > Open...** and navigating to the respective project directory.

6. **Edit the Run Configuration for Each Project**:
   - For Admin Portal:
     - Navigate to **Run > Edit Configurations...**.
     - Click on the **+** icon to add a new configuration and select **Spring Boot**.
     - In the **Main class** field, specify the main application class.
     - Ensure the **Working directory** is set to the project root directory.
     - Set the port to 8080 in the VM options: `-Dserver.port=8080`
     - Click **OK** to save the configuration.
   
   - For User Portal:
     - Repeat the above steps but set the port to 8081 in the VM options: `-Dserver.port=8081`

7. **Configure Application Properties**:
   - For each project, open `src/main/resources/application.properties` or `application.yml` to configure:
     - Database connection (local or AWS RDS)
     - OAuth configurations for Google and Facebook (in User Portal)
     - PayPal API credentials
   
   - For AWS S3 credentials, set up the following environment variables on your system for security:
     - `AWS_ACCESS_KEY_ID`
     - `AWS_SECRET_ACCESS_KEY`
     - `AWS_BUCKET_NAME`
     - `AWS_REGION`

8. **Initial Website Settings Setup**:
   - After first launching the Admin Portal, navigate to the Settings page to configure:
     - **Currency Settings**: Select your default currency for the store. If currency selection is null, run the currency test to populate available currencies.
     - **Countries and States**: Configure shipping destinations. Run the countries and states test if data is not already populated.
     - **Payment Settings**: Configure PayPal credentials and other payment options.
     - **Email Settings**: Set up SMTP server details for order confirmation and user verification emails.
   
   - These settings are essential for proper functioning of both admin and user portals.

9. **Run Both Applications**:
   - Start the Admin Portal first, then the User Portal.
   - You can run each application by clicking the green **Run** button or by selecting **Run > Run 'YourConfigurationName'** for each project.

### 3. Access the Applications

- **Admin Portal**: Access at `http://localhost:8080/NexkartAdmin` 
  - Default admin account: `admin@gmail.com` with password `admin`

- **User Portal**: Access at `http://localhost:8081/Nexkart`
  - First setup the settings module in the admin page and then create new user 

Note: For production deployment on Heroku, additional configuration for environment variables and build packs may be required. Refer to the Heroku documentation for detailed deployment instructions.
