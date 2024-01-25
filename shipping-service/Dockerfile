FROM python:3.12.1-bullseye

WORKDIR /app

COPY Pipfile Pipfile.lock /app/

RUN pip install --no-cache-dir pipenv && \
    pipenv install --deploy --ignore-pipfile

COPY . /app

ENV FLASK_ENV=development
ENV FLASK_APP=app.py

CMD ["pipenv", "run", "flask", "run", "--host=0.0.0.0", "--port=8090"]
