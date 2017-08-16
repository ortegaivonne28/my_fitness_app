# My Fitness App

**My fitness app** is an app lets users track water intake, create routines or workouts and set them to a specific day of the week and then track the progress the user is making. The app also allows the users to add friends and challenges them to complete a workout.


This app was built as a my final project for **General Assembly’s Android development Bootcamp**.

## Project Details

The app uses firebase to store the user information as well as the user's routines and workouts. The history (workouts done) and saved in a local database.

Each time a user adds a friend or challenges a friend, firebase pulls the new data and a broadcast reciver is created to let the user know of the new changed.

When the user sets a new routine, the app creates the routine starting on a Monday. The next monday is calculated and the next 30 Mondays after that so the user can choose which Monday to start the routine.

One of the main things that set this app as a challenge was how big the Database got. Since the user is creating routines, the routines can last for several weeks and each day of the week can be different and each week can be different as well. Then each week is going to have 7 days. Each day the user can have a workout, a challenge and a cardio session, as well as tracking their water intake and weight. Each workout is also going to have exercises, those exercises can be 3 different kind of exercises: Single Exercise, Super Set or Triple set.

When addind exercises and weeks, a recycler view is used. The last position of the recycler view is always going to be a custom view to keep adding things.


## Features include:

- Ability to create routines and workouts.
- Track water intake.
- Set a routine for several weeks or months at a time.
- Delete routines and workouts.
- Add friends.
- Challenge friends using one of the custom workouts created in the app




#### Skills/languages/tools: Java, Android SDK, SQLite, Services, Threads, Firebase, Notifications.

## Screenshots

![image](/screenshots/1.png)
![image](/screenshots/2.png)
![image](/screenshots/3.png)
![image](/screenshots/4.png)
![image](/screenshots/5.png)




