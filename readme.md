
# Spring Boot Lesson 20
### Updated for Spring Boot 2.0.3


Sample data is below. You can paste this into localhost:8080/h2 after the first run. If the encrypted passwords don't work then simply change each to 'password' and uncomment the appropriate code in User.getPassword()
```sql
insert into role values (1,'USER');
insert into role values (2, 'ADMIN');
insert into USER values (1, 'jim@jim.com', TRUE, 'Jim', 'Jimmerson', '$2a$10$UiEzkUUnqIUZ/G.TkBvGseoMM.1SnyDtKI0bilHnMhpwj0ZWoocIG', 'jim');
insert into USER values (2, 'bob@bob.com', TRUE, 'Bob', 'Bobberson', '$2a$10$UiEzkUUnqIUZ/G.TkBvGseoMM.1SnyDtKI0bilHnMhpwj0ZWoocIG', 'bob');
insert into USER values (3, 'admin@admin.com', TRUE, 'Admin', 'User', '$2a$10$yN/fZnL7rCHgkM3BSjbfQ.KPe.5IxvuwnaDvFnbbJwi7S/JvhYXY.', 'admin');
insert into USER_ROLES values (1,1);
insert into USER_ROLES values (2,1);
insert into USER_ROLES values (3,1);
insert into USER_ROLES values (3,2);
```


## What does bcrypt do?
* BCrypt Generates a random salt. 
* A "cost" factor has been pre-configured. 
* Encypts a password.

Derive an encryption key from the password using the salt and cost factor. 
Use it to encrypt a well-known string. Store the cost, salt, and cipher text. 

Because these three elements have a known length, 
it's easy to concatenate them and store them in a single field, 
yet be able to split them apart later.

When someone tries to authenticate, retrieve the stored cost and salt. 
Derive a key from the input password, cost and salt. 
Encrypt the same well-known string. 
If the generated cipher text matches the stored cipher text, the password is a match.

Bcrypt operates in a very similar manner to more traditional schemes 
based on algorithms like PBKDF2. 
The main difference is its use of a derived key to encrypt known plain text; 
other schemes (reasonably) assume the key derivation function is irreversible, 
and store the derived key directly.

Stored in the database, a bcrypt "hash" might look something like this:

```$2a$10$vI8aWBnW3fID.ZQ4/zo1G.q1lRps.9cGLcZEiGDMVr5yUP1KUOYTa```

This is actually three fields, delimited by "$":

* ```2a``` identifies the bcrypt algorithm version that was used.
* ```10``` is the cost factor; 210 iterations of the key derivation function are used (which is not enough, by the way. I'd recommend a cost of 12 or more.)
* ```vI8aWBnW3fID.ZQ4/zo1G.q1lRps.9cGLcZEiGDMVr5yUP1KUOYTa``` is the salt and the cipher text, concatenated and encoded in a modified Base-64. The first 22 characters decode to a 16-byte value for the salt. The remaining characters are cipher text to be compared for authentication.