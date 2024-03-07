document.addEventListener('DOMContentLoaded', function() {
    const originalFetch = window.fetch;
    window.fetch = function(url, options) {
            if (!options.headers) options.headers = {};
            options.headers['Authorization'] = 'Bearer eyJhbGciOiJFUzUxMiIsImtpZCI6ImUyYjhkNWY5LTZmODItNDA1Ny04MDQ5LTUxMzM5MzM4MGEwOSIsInR5cCI6IkpXVCJ9.eyJhdHRyIjp7InRlcm1zX2FuZF9jb25kaXRpb25zIjpbIjE3MDk3NjgxNzIiXX0sImF1ZCI6WyI3ZGVmYTZkZi03NTE0LTQ0YTItODgwNi1iMDYxMDkyYmM4ZmEiXSwiZW1haWwiOiJjdXN0b21lckBlbWFpbC5jb20iLCJlbWFpbFZlcmlmaWVkIjp0cnVlLCJleHAiOjE3MDk4Mzg3MDUsImdyb3VwcyI6WyJ1c2VyIl0sImlhdCI6MTcwOTgzODQwNSwiaWRwSUQiOiJiZTUwNzdlZC1iMGE3LWIxZWQtYmU1Ny1kMDBkMWVlZmZlYzciLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDIvYXV0aC9vaWRjL29wL0N1c3RvbWVyIiwianRpIjoiODA0OTY2NjYtODQ2Zi00ZTI3LWE0NDctMTM4NGJmYTRjZDZmIiwibmJmIjoxNzA5ODM4NDA1LCJzc29TdWJqZWN0IjoiMTcyOTA5MDktMzAxNi00ZjYzLWI2MDEtZTMwNDEwZjFiMDVmIiwic3QiOiJvaWRjIiwic3ViIjoiMTcyOTA5MDktMzAxNi00ZjYzLWI2MDEtZTMwNDEwZjFiMDVmIiwidmFsaWRGb3JQb3J0YWwiOnRydWV9.AOQmKeEFMTnetAMJQtjEx6I4NQfVmLQHoGVeBMxzVYQOsBI6bhX5UYTyhMYSkVFbLPVW7_a8j8m_GBYP4c_w0ipoAe6bF8ztaIXMwX58c9d9QY3A6rLb1WMRxCIGo1lRSc12FXTmmli5XAm5UmqA5RPyMEUu8-JcjvFDtX3yuEq0pRW3';
            return originalFetch(url, options);
          };
 });
