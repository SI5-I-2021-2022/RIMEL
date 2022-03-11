


from parserUtils import ListFiles, findEndIndex


def findPrecondition(ListFile):
    count=0
    res = {}

    for File in ListFile:
        file1 = open(File, 'r')
        
        body = file1.read()
        startIndex=body.strip().find(".addPrecondition")
        
        if (startIndex>=0):
            fileName = File.split('\\')[-1]
            res[fileName] = []
            
        while (startIndex >= 0):
            stringToEvaluate=body[startIndex:len(body)]
            endIndex=findEndIndex(stringToEvaluate)
            
            code = body[startIndex:startIndex+endIndex]
            res[fileName].append(code)

            count=count+1
            body=body[startIndex+endIndex:]
            startIndex=body.strip().find(".addPrecondition")
        file1.close()
    return res


def run():
    listPreCond = findPrecondition(ListFiles)

if __name__ == "__main__":
    run()