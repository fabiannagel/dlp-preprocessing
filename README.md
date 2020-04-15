# dlp-preprocessing

This is a preprocessing and conversion tool that I built for a student project in deep learning back in 2018.
We were given a labeled dataset of motorcyclists in various cities in Myanmar. Our goal was to detect inidividual bikers and extract how many people are on board as well as who of them is wearing a helmet.
The data extraction part was posed as a classification problem with 36 individual classes, representing every possible combination of 1 to 4 passengers with individual binary information about helmet usage.
Since we wanted to solve both the detection and classification task in one go, we decided to go with YOLOv3 and the corresponding [Darknet framework](https://github.com/pjreddie/darknet). 

Our training data was structured in the following way:
- Images of every individual roadside camera are located in a unique folder, e.g. "Mandalay_01"
- In the common root, there is a CSV file containing the bounding box annotations, e.g. "Mandalay_01.csv"

This tool:
- reads the CSV files and represents it in an appropriate POJO datastructure
- performs some data cleaning for obvious labeling errors, e.g. negative bounding box dimensions
- transforms absolute bounding box coordinates to relative dimensions 
- converts everything to a representation that we can use with darknet
