# Wildfire Insurance Inventory System

A Java-based inventory management system designed to help wildfire victims maintain a detailed catalog of their household items for insurance claims.

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── wildfireinventory/
│   │           ├── dao/
│   │           ├── model/
│   │           └── service/
│   └── resources/
└── db/
```

## Prerequisites

- Java JDK 17 or higher
- PostgreSQL 13 or higher
- PostgreSQL JDBC Driver (postgresql-42.x.x.jar)

## Setup Instructions

1. Clone the repository
2. Set up PostgreSQL database:
   - The project uses Supabase as the database backend
   - No local database setup is required
   - The schema will be created automatically in your Supabase project's default database
3. Add PostgreSQL JDBC driver to your project:
   - Download from: https://jdbc.postgresql.org/download.html
   - Add postgresql-42.x.x.jar to your project's build path

## Project Dependencies

- mysql-connector-java
- lombok (for reducing boilerplate code)

## Database Schema

The system uses a relational database with the following main tables:
- Users: Stores user information
- Rooms: Represents different rooms in a user's home
- Categories: Optional categorization for items
- Items: Detailed inventory of items with their properties

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
