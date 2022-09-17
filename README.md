# NPL-classifier

Text classifier using [Cosine similarity](https://en.wikipedia.org/wiki/Cosine_similarity).

This project could be used to identify spam messages, tell the mood of a text, 
the language in which a text is written or even the subject of the text; 
if the text is taking about sports, politics, tech, etc... 

Any type of text classification is posible with the correct data :)

## Manual

Takes 2 arguments; a folder path and an optional [n-gram](https://en.wikipedia.org/wiki/N-gram) number (default is 2).

The folder path is expected to have a mystery text inside, and a group of folders,
the folders represent the categories in which the mistery text will be classified.

## Functionality 
A thread will be created for each category, and these threads will create a thread for 
each file inside the folder. There is a global [histogram](https://en.wikipedia.org/wiki/Histogram)
for each category that will be accessed synchronously by the threads of each folder.
Therefore, all files will be processed concurrently.

## Examples

#### classify language

**Mystery fiel:**
```
This is a mystery text in some language
```

**Folder structure:**
```
LocalFolder/
├── al
│   └── f1.txt
├── en
│   ├── f1.txt
│   └── f2.txt
├── es
│   ├── f1.txt
│   └── f2.txt
├── fr
│   ├── f1.txt
│   ├── f2.txt
│   ├── f3.txt
│   └── f4.txt
├── ge
│   ├── f1.txt
│   ├── f2.txt
│   └── f3.txt
├── it
│   ├── f1.txt
│   ├── f2.txt
│   ├── f3.txt
│   ├── f4.txt
│   ├── f5.txt
│   └── f6.txt
└── mystery.txt
```
**output:**
```
al angle: 79.752535
en angle: 67.709670
it angle: 73.984345
fr angle: 71.602961
ge angle: 68.715449
es angle: 73.536767
Closest: en angle: 67.709670
```

The data inside the folder ***en*** is the most similar to the mistery file. 

