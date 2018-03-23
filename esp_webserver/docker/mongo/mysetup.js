db.createUser( {
    user: "mongodbuser",
    pwd: "mongodbuser-password",
    //predefined role
    roles: [ { role: "root", db: "admin" } ]
});

db.shutdownServer({timeoutSecs: 60});
