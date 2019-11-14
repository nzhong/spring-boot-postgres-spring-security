# Start a PostgreSQL instance via docker


- create a folder to hold the DB data, in this example the folder is /Users/ningzhong/dev/docker/pg1
- then
```
docker run --name pg1 \
  -e POSTGRES_PASSWORD=welcome \
  -p 5432:5432 \
  -v /Users/ningzhong/dev/docker/pg1:/var/lib/postgresql/data \
  -d postgres:12.0
```

- then use command line (or some GUI tool) to connect to the newly started instance
```
CREATE TABLE app_user (
  id          SERIAL PRIMARY KEY,
  username    varchar(255) NOT NULL DEFAULT '',
  password    varchar(255) NOT NULL DEFAULT '',
  activities  JSONB,
  UNIQUE (username)
);
```

- by now we should have a DB instance running on port 5432 | username=postgres | password=welcome | table app_user under the default 'postgres' db.
