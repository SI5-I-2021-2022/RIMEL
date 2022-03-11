# from parserError import additionnalCheckDict, errorDict, mandatoryDict
# from parserOverride import overrideDict
from parserPrecondition import findPrecondition
from parserUtils import ListFiles, preconditionDict










print(preconditionDict(ListFiles))
listPreCond = findPrecondition(ListFiles)
print(listPreCond)
# overridePreCond = overrideDict(listPreCond)
# mandatoryOverride = mandatoryDict(overridePreCond)
# addCheckOverride = additionnalCheckDict(listPreCond)
# errorAddCheck = errorDict(addCheckOverride)

# for key in errorAddCheck:
#     print(key+"--------------------------------------------------------")
#     for cond in errorAddCheck[key]:
#         print(cond)
#         print("\n")