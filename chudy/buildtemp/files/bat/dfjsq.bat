rem It is one command to generate dataflow js files according to component xml quickly
rem Give module id and component category (source or target) as parameters

call dfjs D:\jedi\yoda\unbundled\af\java\dataflow\modules\%1module\components\%1%2\component.xml
