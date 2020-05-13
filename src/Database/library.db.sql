DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS "user_notReg";
DROP TABLE IF EXISTS "LibroCard";
DROP TABLE IF EXISTS "order";
DROP TABLE IF EXISTS "book";
DROP TABLE IF EXISTS "composition";
DROP TABLE IF EXISTS "cart";
DROP TABLE IF EXISTS "employee";

CREATE TABLE IF NOT EXISTS "user" (
	"name"         TEXT NOT NULL,
	"surname"      TEXT NOT NULL,
	"phone"	       TEXT NOT NULL UNIQUE,
	"email"	       TEXT NOT NULL,
	"password"     TEXT NOT NULL UNIQUE,
	"homeAddress"  TEXT NOT NULL,
	"streetNumber" TEXT NOT NULL,
	"ZIPcode"      TEXT NOT NULL,
	"homeCity"     TEXT NOT NULL,
	PRIMARY KEY("email")
);

CREATE TABLE IF NOT EXISTS "user_notReg" (
    "name"         TEXT NOT NULL,
    "surname"      TEXT NOT NULL,
    "phone"	       TEXT NOT NULL UNIQUE,
    "email"	       TEXT NOT NULL,
    PRIMARY KEY ("email")
);

CREATE TABLE IF NOT EXISTS "LibroCard" (
	"cardID"    TEXT NOT NULL,
	"issueDate" TEXT NOT NULL CHECK ( issueDate LIKE '[0-9]{2}/[0-9]{2}/[0-9]{4}'),
	"points"    INTEGER DEFAULT 0,
	"user"      TEXT NOT NULL,
    PRIMARY KEY("cardID"),
    FOREIGN KEY ("user") REFERENCES "user"("email")
);

CREATE TABLE IF NOT EXISTS "order" (
	"orderID"         TEXT NOT NULL,
	"date"	          TEXT NOT NULL CHECK ( date LIKE '[0-9]{2}/[0-9]{2}/[0-9]{4}'),
	"status"	      TEXT NOT NULL,
	"paymentMethod"	  TEXT NOT NULL,
	"price"	          NUMERIC NOT NULL DEFAULT 0.00,
	"points"	      INTEGER DEFAULT 0,
	"shippingAddress" TEXT NOT NULL,
    "user"	          TEXT,
    "user_notReg"     TEXT,
    FOREIGN KEY ("user") REFERENCES "user"("email"),
    FOREIGN KEY ("user_notReg") REFERENCES "user_notReg"("email"),
	PRIMARY KEY("orderID")
);

CREATE TABLE IF NOT EXISTS "composition" (
    "book"	    TEXT NOT NULL,
    "order"	    TEXT NOT NULL,
    "quantity"  INTEGER DEFAULT 1,
    PRIMARY KEY ("book", "order"),
    FOREIGN KEY("book") REFERENCES "book"("ISBN"),
    FOREIGN KEY("order") REFERENCES "order"("orderID")
);

CREATE TABLE IF NOT EXISTS "book" (
	"ISBN"            TEXT CHECK ( ISBN LIKE '[0-9]{3}-[0-9]{2}-[0-9]{5}-[0-9]{2}-[0-9]') NOT NULL,
	"title"	          TEXT NOT NULL,
	"authors"         TEXT NOT NULL,
	"genre"	          TEXT,
	"price"	          NUMERIC NOT NULL DEFAULT 0.00 CHECK(price >= 0.00),
	"description"     TEXT,
	"publishingHouse" TEXT NOT NULL,
	"publishingYear"  INTEGER NOT NULL CHECK ( publishingYear BETWEEN 0 AND 2020),
	"discount"       NUMERIC DEFAULT 0.00 CHECK ("discount" BETWEEN 0 AND 100),
	"availableCopies" INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY("ISBN")
);

CREATE TABLE IF NOT EXISTS "cart" (
    "user"      TEXT NOT NULL,
    "book"      TEXT NOT NULL,
    "quantity"  INTEGER DEFAULT 1,
    PRIMARY KEY ("user", "book"),
    FOREIGN KEY ("user") REFERENCES "user"("email"),
    FOREIGN KEY ("book") REFERENCES "book"("ISBN")
);

CREATE TABLE IF NOT EXISTS "employee" (
    "employeeID"    TEXT NOT NULL,
    "name"          TEXT NOT NULL,
    "surname"       TEXT NOT NULL,
    "birthDate"     TEXT NOT NULL CHECK ( birthDate LIKE '[0-9]{2}/[0-9]{2}/[0-9]{4}'),
    "role"          TEXT NOT NULL,
    "employedSince"  TEXT NOT NULL CHECK ( employedSince LIKE '[0-9]{2}/[0-9]{2}/[0-9]{4}'),
    PRIMARY KEY ("employeeID")
);

