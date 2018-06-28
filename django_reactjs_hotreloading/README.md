## Credit to https://owais.lone.pw/blog/webpack-plus-reactjs-and-django/, github: https://github.com/owais/django-webpack-loader

## 1. Prepare environment
- Add all Django dependencies
```
virtualenv venv
source venv/bin/active
pip install -r requirements.txt
```

- Add all ReactJS dependencies
```
npm install
```

## In Local Build mode (React compiled into one file, not recommended)
```
npm run build
python manage.py runserver
```
notice: comment the publicPath line in base/webpack.local.config.js first.

## Run In Hot Reloading mode (React in Memory, Recommend for development)
```
npm start
python manage.py runserver
```

## Build for Production
```
npm run build-production
python manage.py collectstatic
```
notice: Other production configuration for Django is required. Sucha as set setting.py -> DEBUG = False, add ALLOWED_HOSTS ...