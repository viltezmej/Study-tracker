## Study tracker

This is a project, which follows OOP principles. The app "Study tracker" starts a timer, which helps the user focus on their tasks for a set amount of time.


## Requirements on how to run the project:
- Java JDK 17 (or higher)

## How to use

- Run the program while on Intellij;
- Press "Start";
- Enter in an amount of time for how long You wish to study for;
- After doing so, wait for the timer to run out, or press "Break" to start a new timer for a break;
- Review old study sessions in the logs;
- Press 'X' on the window's border to exit the program;

## Reusable modules

# Logic #
SessionManager:
- startSession() – Starts a new timer.
- tick() – Must be called every second to count down the time.
- toggleBreak() – Pauses / resumes the session.
- endSessionEarly() – Ends the session early.

SessionLog:
- getFormattedDate() - Formats the session timestamp into yyyy-MM-dd HH:mm

# GUI #
Theme:
- Whole class can be reused, if one wishes to have a similar color scheme to the tracker app

SessionSetupPanel:
- reset() - Resets entered in number
- handleBeginClicked() - Checks for valid input
- showError() - Outputs invalid outcome

MainFrame (Facade):
- setUpGUI() - Configures frame and shows it
- startActiveSession - Sets up active session panel for new session and shows it
- endActiveSession - Stops timer and session early.

HomePanel:
- Contains actions which switches views
- Displays user's statistics

ActiveSessionPanel:
- formatTime() - Formats the default time to HH:MM:SS
- startTimer() / stopTimer() - Manages GUI timer 

SessionPanel:
- refresh() - Gets latest logs, builds a formated table
- shortenSubject() - Shortens longer names
