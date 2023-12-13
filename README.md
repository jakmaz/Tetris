# Pentomino solver - Group 20  
 12.12.2023
 ### PURPOSE OF PROJECT
The purpose of this project is to develop an intelligent agent capable of playing and solving the classic game of Tetris. The agent uses different strategies and algorithms to optimize block placement, clear lines effectively, and score as many points as possible. It showcases the application of heuristic search, algorithm optimization, and the implementation of AI techniques in game playing.

We implemented the following bots:

1.  **Random Bot:** This bot places Tetrominoes randomly on the grid without any strategic planning or foresight. It serves as a baseline to evaluate the performance of more sophisticated algorithms.
    
2.  **Heuristic Bot:** The bot employs a heuristic algorithm to evaluate the best move by considering the current state of the grid. Its decision-making process involves choosing moves that clear lines, minimize the stack's height, and avoid creating gaps that are difficult to fill.
    
3.  **Heuristic Bot with knowledge of the next piece:** This advanced bot not only considers the current state of the grid but also plans ahead by taking into account the next Tetromino. It calculates the best move by predicting how the current and next pieces can work together to improve the game state, clear more lines, and avoid creating problematic situations for future moves.

 ### HOW TO START THIS PROJECT
 
1.  **Open the src Directory:** Navigate to the project directory where the Java source code is located.    

Using Jar file
   
2.  **Run jar file:** You should see a "Tetris" jar file. Run it.

Using Java Compilator
    
2.  **Go to "tetris" package:** Navigate to "tetris directory" 
3.  **Compile the Main Class:** `javac Main.java`
4. **Run the Project:** `java Main`

 ### USER INSTRUCTIONS
- **Theme:**
    You have an option to change between Dark mode and Light mode. Before playing the game, user can toggle between these two options.
    
- **Play the game:**
User can start playing the game by clicking the button Play.
User will be displayed with three blocks. First block displays the tetris grid to play the game. Second block displays the score board and the third block displays the next block that will be chosen for play.
Pentominoes starts falling from the top of the grid. User can rotate the block by pressing up aero key. User can move the block right, left and bottom by pressing the right, left and down aero keys respectively.

-  **Bot Game:**
We have three options to choose – Random bot, Heuristic bot, and Heuristic bot with knowledge of next move.

- **Leaderboards:**
User can store the score at end of each game by giving name as user input. Later, high scores can be viewed by pressing the button High Scores.


 ### AUTHORS
- Dario Dastan  
- Anil Koca  
- Ethan de Beer   
- Tristan Dormans  
- Jacob Mazur  
- Lakshana Sivaprakash 
- Bassam Tayba