## Create to https://cloud.tencent.com/developer/article/1053102

## Backend:
```
python manage.py migrate
python manage.py createsuperuser
python manage.py runserver 0.0.0.0:8000
```

### Backend test:
```
curl -X POST -d "username=<your username>&password=<your password>" http://localhost:8000/auth
```


## Frontend:    
Install all the dependencies
```
npm install --save-dev babel-preset-es2015 babel-preset-stage-3
npm install --save redux redux-logger redux-persist react-redux
npm install --save axios react-router-dom lodash
```

### Dev
```
npm start
```

### Prod
```
npm run build
```