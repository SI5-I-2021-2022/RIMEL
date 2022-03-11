from parserOverride import overrideDict
from parserPrecondition import findPrecondition
from parserUtils import ListFiles


def mandatoryDict(listPreCond):
    res = {}

    for key in listPreCond:
        for cond in listPreCond[key]:
            startInd = cond.find("isMandatory")
            if(startInd >0):
                endInd = findTextBetweenChar(cond[startInd:],'{','}')
                mandatoryCode = cond[startInd:startInd+endInd]
                if(key in res):
                    res[key].append(mandatoryCode)
                else:
                    res[key] = [mandatoryCode]
    return res  

def additionnalCheckDict(listPreCond):
    res = {}

    for key in listPreCond:
        for cond in listPreCond[key]:
            startInd = cond.find("makeAdditionalChecks")
            if(startInd >0):
                endInd = findTextBetweenChar(cond[startInd:],'{','}')
                addCheckCode = cond[startInd:startInd+endInd]
                if(key in res):
                    res[key].append(addCheckCode)
                else:
                    res[key] = [addCheckCode]
    return res  


def errorDict(listAddCheckOverride):
    res = {}

    for key in listAddCheckOverride:
        for code in listAddCheckOverride[key]:
            startInd = code.find("Error(") #createError
            while(startInd >0):
                endInd = findTextBetweenChar(code[startInd:],'(',')')
                errorCode = code[startInd:startInd+endInd]
                if(key in res):
                    res[key].append(errorCode)
                else:
                    res[key] = [errorCode]
                code = code[endInd:]
                startInd = code.find("Error(") #createError

    return res  

def findTextBetweenChar(string,charOpen,charClose):
    foundFirst = False
    count=0
    for i in range(len(string)):
        if string[i]==charOpen:
            foundFirst = True
            count=count+1
        elif string[i]==charClose:
            count=count-1
        elif count==0 and foundFirst:
            return i
    return None 

def run():
    listPreCond = findPrecondition(ListFiles)
    overridePreCond = overrideDict(listPreCond)
    mandatoryOverride = mandatoryDict(overridePreCond)
    addCheckOverride = additionnalCheckDict(listPreCond)
    errorAddCheck = errorDict(addCheckOverride)

if __name__ == "__main__":
    run()