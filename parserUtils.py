import glob2
import csv

ListFiles=glob2.glob(r".\rapidminer-studio-modular-master\rapidminer-studio-core\src\main\java\com\rapidminer\operator\**/*.java")


def preconditionDict(ListFile):
    dictList = {}
    for File in ListFile:
        file1 = open(File, 'r')
        body = file1.read()
        startIndex=body.strip().find(".addPrecondition")
        
        while (startIndex >= 0):
            body=getPreconditonNameAndIfOverrode(body,startIndex,dictList,File)
            startIndex=body.strip().find(".addPrecondition")
        file1.close()
    printCSV(dictList)

def getPreconditonNameAndIfOverrode(body,startIndex,dictList,File):
    override= "Not Overrode"
    stringToEvaluate=body[startIndex:len(body)]
    startNamePrecondition = stringToEvaluate.strip().find("new ")
    #to avoid taking comments
    if startNamePrecondition<50:
        startIndex+=startNamePrecondition+4
        stringToEvaluate=body[startIndex:len(body)]
        endIndex=findNextParenthesis(stringToEvaluate)
    else:
        endIndex = startIndex+51

    if(endIndex-startIndex<50):
        stringToEvaluate=body[startIndex-(startNamePrecondition+4):len(body)]
        endPreconditionIndex=findEndIndex(stringToEvaluate)
        if body[startIndex-(startNamePrecondition+4):startIndex+endPreconditionIndex].strip().find("@Override")>0:
            override="Overrode"
        if File in dictList:
            dictList[File].append([body[startIndex:startIndex+endIndex],override])
        else :
            dictList[File]=[[body[startIndex:startIndex+endIndex],override]]
    if(endIndex != None):
        body=body[startIndex+endIndex:]
    else:
        body=body[startIndex:]
    return body
    

def printCSV(myDict):
    csv_columns = ['fileName']
    for elem in myDict:
        for precondition in myDict[elem]:
            if precondition[0] not in csv_columns:
                csv_columns.append(precondition[0])
    dict_data=[]
    for elem in myDict:
        data = {}
        data[csv_columns[0]]=elem.split('\\')[-1]
        for column in csv_columns[1:]:
            count = 0
            for precondition in myDict[elem]:
                if precondition[0] == column:
                    count+=1
            print(column)
            print(count)
            data[column]=count
            print(data) 
        dict_data.append(data)
    csv_file = "Nombre_Preconditions_par_fichier_java.csv"
    try:
        with open(csv_file, 'w',newline='') as csvfile:
            writer = csv.DictWriter(csvfile, fieldnames=csv_columns)
            writer.writeheader()
            for data in dict_data:
                writer.writerow(data)
    except IOError:
        print("I/O error")

def findNextParenthesis(string):
    for i in range(len(string)):
        if (string[i]=='('):
            return i
    return None    

def findEndIndex(string):
    count=0
    for i in range(len(string)):
        if ((string[i]=='{') or (string[i]=='(')):
            count=count+1
        if ((string[i]=='}') or (string[i]==')')):
            count=count-1
        if (count==0 and string[i]==";"):
            return i
    return None