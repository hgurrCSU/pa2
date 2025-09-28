[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=20613591)
# PA2 - Music taste analysis
# Motivation

To build effective music recommendation systems, we must understand individual user preferences and filter low-quality data. Using the same input format from PA1, rather than calculating statistics for each song, we now want to analyze **rating patterns of users**. We're doing this by creating a **uniform list for each user's rating for each song**, also showing which songs they didn't rate. 

Also, we can't expect our data to always be squeaky clean. From now on, there can be **potential errors** in the dataset that your code should be able to handle. You'll also be tasked to identify and remove **uncooperative users** without rating diversity. Lastly, to be able to compare users effectively, missing ratings should be filled with a placeholder rating. 

There will now be **two ways** to run your program. Depending on the arguments your code should either calculate Song Stats (PA1) or output a User Analysis (PA2). Read more in the Running Your Program section.

# Tasks

The input is still the exact same format as the CSV from PA1.

```
song1,user1,rating 
song1,user2,rating 
song2,user1,rating 
...
```

**User Profile Generation** 
First, we want to process the dataset to generate comprehensive profiles for all users. Begin by collecting all **unique user names** and sorting them alphabetically. Then we want to make sure the profiles are created in a way that we are able to compare them to each other. 
For this, identify **every distinct song** that appears in the entire database (across all cooperative users - see next section), sorting these song names alphabetically. In your output file, start with the header "`username,song,rating`". Then for every user, list every song and the corresponding rating. If a user has not rated a song, you will fill in a signal value `Double.NaN`. "NaN" stands for "Not A Number" and is used as a standard way to replace missing values to avoid accidentally including them in calculations, which could happen with using a signal value like 0 or -1.
This also means that you should treat all your ratings as a `Double`.

Example (there are more examples further down):
```
username,song,rating
user1,song1,4
user1,song2,NaN
user1,song3,1
user2,song1,2
user2,song2,4
user2,song3,5
```

_Implementation Hint_: For this task we're looking at a **nested data structure** (*User -> Song -> Rating*). Consider using **nested maps** where the first key represents users and the second key represents songs! This will enable you to just query a user's rating of a song without having to loop through lists.

**Filtering Uncooperative Users**  
Apps like this often have a lot of accounts that aren't used or bot accounts. We don't want to use unhelpful data for our features, so we want to remove these uncooperative users from our dataset. We count users as uncooperative if they have only one rated song or gave all songs the same rating. To cover both cases, exclude any user who provided **only one distinct rating value across all their rated songs**. For example:

- A user who rated three different songs as [3, 3, 3] should be removed
- A user who rated songs as [4, 5, 4] should be retained (two distinct values: 4 and 5)

If a song appears only in a rating list of an uncooperative user, **that song should not appear on the final list** of other users. We'll just act like all ratings by that user never existed in our database.

## Error Cases
From now on, there can be potential errors in the dataset that your code should be able to handle (see section #HandlingTheErrors). The following error cases apply to **both PA1 and PA2!** To avoid duplicate code, consider checking for these errors every time you read the launch arguments and the CSV data file.

The user could run the program incorrectly
* Provide the incorrect number of arguments.

There can be many issues with the input file such as
* The file doesn't exist at the provided path.
* The file is empty and has no contents.
* The file is not a CSV and thus does not have the .csv file extension.

There can be issues with the output file such as
* The path to the output file doesn't exist. For example, if the program is run with the output file path as `fake/directory/output.csv` and the path `fake/directory/` doesn't exist then the program has hit an error case. Treat this as an error by throwing an exception and gracefully exiting. Do not create the correct directory path in an attempt to recover from the error.
  * On the other hand, if the program is run with the output file path as `output/output.csv` and the path `output/` exists then the output.csv file should be created as normal and the contents should be the same as PA1.

There are could also be issues with the contents of the file such as
* The rating is not an integer. A double or a float is not an integer. Therefore, coming across a rating of 3.0 is an error. Do not cast the double or float to an integer.
* The rating is not between 1 and 5. Meaning an error case can arise from a rating that could be below or above this range.
* The entry has extra or missing fields. Meaning there are not the 3 fields (song, user, rating) in the row. For example, an incomplete entry may only have the song and rating and thus be missing the user. 

## Handling the Errors

### Error Messages
In the event of an error, your program **_must_** write an error message to `System.err`. Do not write to `System.out` as that is for standard output. `System.err` is for error output. The error message **_must_** start with the string "Error". Then include the description of the error. Anything written to `System.err` will appear in the console when running `gradle run -q --args="<input-file> <output-file>"`

The error message should be descriptive and describe the error to the user. For example, "Error: with file badInput.csv" is not descriptive, but "Error: file badInput.csv does not exist" tells the user what the error is. After printing the error to `System.err`, your program should end gracefully.

### Graceful Exit
In the event of an error, after a descriptive error message is printed to `System.err` then the program must gracefully exit. Not gracefully exiting would be allowing the error to go undetected and unhandled causing your program to continue processing or crashing unexpectedly. Utilize exception handling to gracefully end your program.

### Running your program
There are now **two different ways** to run your program. The first argument is the input CSV file and the second argument is the output CSV file. Just as in PA1 and PA2, when two arguments are passed to your program, you will write the song rating stats to a CSV file corresponding to the second arg. When a third argument `-a` is passed to your program, you will write the user taste analysis to a CSV file corresponding to the second arg. These are independent from one another, so **only one file** is created **in either case**, only the contents of the output file changes depending on the presence of the third arg `-a`. To accept command line arguments, utilize the `String[] args` parameter in your main method.

Do not hard code the input or output files. The user of your program must be able to specify which input CSV they want to read from and where and what name they want for the output CSV.

# How to Compile and Run

`gradle build`

`gradle run -q --args="'<input-file>' '<output-file>' '-a'"`

example run `gradle run -q --args="'database/files/file.csv' 'output/output.csv' '-a'"`

# Example Input/Output

command - PA1
```gradle run --args="'database/files/file1.csv' 'song_stats.csv'"```

file1.csv
```
song1,user1,3
song1,user2,2
song1,user3,3
song1,user4,5
song2,user1,5
song2,user2,4
song2,user3,2
song2,user4,5
```

song_stats.csv
```
song,number of ratings,mean,standard deviation
song1,4,3.25,1.0897247358851685
song2,4,4.0,1.224744871391589
```
---

command - PA2
```gradle run --args="'database/files/file2.csv' 'user_analysis.csv' '-a'"```

file2.csv
```
Bohemian Rhapsody,charlie,4
Sweet Home Alabama,alex,4
All Star,alex,3
All Star,charlie,2
Sweet Home Alabama,charlie,3
All Star,cameron,5
```

user_analysis.csv
```
username,song,rating
alex,All Star,3
alex,Bohemian Rhapsody,NaN
alex,Sweet Home Alabama,4
charlie,All Star,2
charlie,Bohemian Rhapsody,4
charlie,Sweet Home Alabama,3
```

---
```gradle run --args="'database/files/file3.csv' 'output.csv' '-a'"```

file3.csv
```
song1,user1,1
song2,user1,2
song3,user1,1
song1,user2,4
song2,user2,5
```

output.csv
```
username,song,rating
user1,song1,1
user1,song2,2
user1,song3,1
user2,song1,4
user2,song2,5
user2,song3,NaN
```

---
```gradle run --args="'database/files/invalid_file_type.txt' 'output.csv' '-a'"```

invalid_file_type.txt
```
song1,user1,3
song1,user2,2
song2,user1,5
song2,user2,4
```
->
console output
```
Error: input and output paths must have `.csv` extension
```

# Submitting Your Homework
Your submission must be in the main branch of your GitHub repository.

# Grading Your Homework
We will pull your code from your repository. It must contain at a minimum two Java files. One must be named `Cs214Project.java`. We will run your program by starting it with that class name. The second file must be named `TestCs214Project.java`. This will be used to run your JUnit tests. Read more on the [JUnit 5 documentation](https://junit.org/junit5/docs/current/user-guide/#writing-tests) on how to write more tests for your assignment. All future assignments must contain these two files at the very least. Although they may contain different code as needed by the particulars of that assignment.

However, we suggest adding more classes/functions as you think about the design of your program. To write clean, maintainable code, you should aim to break your solution into multiple, logically organized classes and functions where appropriate. Here are a few guidelines to follow:
  * Use multiple classes to encapsulate related behaviors and data.
  * Create functions to avoid repetitive code and to make your solution more modular and readable.
  * Follow the Single Responsibility Principle: each class and function should have one clear purpose.
  * Keep your main method focused on orchestrating the flow of the program, rather than handling all the details.

By doing so, your code will not only be easier to read and debug, but it will also help you build a strong foundation in software design principles.

# Hints
* For this task we're looking at a **nested data structure** (User -> Song -> Rating). Consider using **nested maps** where the first key represents users and the second key represents songs! This will enable you to just query a user's rating of a song without looping through lists.
* If you use Double.toString to format your ratings, it will work both for values and for Double.NaN
* Think about how exception handling and try catch blocks could be used to gracefully end your program in the event that unexpected input is encountered. Keep in mind that `main` should not throw an Exception, or you will not pass our tests.
* Consider using appropriate exception classes to catch specific errors. For example, `FileNotFoundException`, `NumberFormatException`, `IOException`, and more.
* To test your program with JUnit consider using [assertThrows(Class<T> expectedType, Executable executable)](https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html#assertThrows-java.lang.Class-org.junit.jupiter.api.function.Executable-)
* When replicating errors, be sure to isolate them in testing. Meaning if you're testing potential errors with the rating value, make one file that has a rating that isn't an integer, a separate file that has a rating below the range, and another separate file for a rating above the range. Do not make one file with all the potential rating value error cases; then how will you be able to decipher which case your program will catch or even miss?
* Test and develop your program on the department Linux machines. That is where we will evaluate it. Your grade depends on how your program performs on those machines. (Warning: Java may behave differently across different environments.)