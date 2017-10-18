#
# A simple makefile for compiling three java classes
#

# define a makefile variable for the java compiler
#
JCC = javac

# define a makefile variable for compilation flags
# the -g flag compiles with debugging information
#
JFLAGS = -g

# typing 'make' will invoke the first target entry in the makefile 
# (the default one in this case)
#
default: DataSetReader.class FPNode.class FPTree.class
# FPTree.class FPNode.class
# AssociationRule.class FrequencyComparator.class fptminer.class

# this target entry builds the Average class
# 
# and the rule associated with this entry gives the command to create it
#
DataSetReader.class: DataSetReader.java
	$(JCC) $(JFLAGS) DataSetReader.java

FPNode.class: FPNode.java
	$(JCC) $(JFLAGS) FPNode.java

FPTree.class: FPTree.java
	$(JCC) $(JFLAGS) FPTree.java

AssociationRule.class: AssociationRule.java
	$(JCC) $(JFLAGS) AssociationRule.java

FrequencyComparator.class: FrequencyComparator.java
	$(JCC) $(JFLAGS) FrequencyComparator.java

fptminer.class: fptminer.java
	$(JCC) $(JFLAGS) fptminer.java


# To start over from scratch, type 'make clean'.  
# Removes all .class files, so that the next make rebuilds them
#
clean: 
	$(RM) *.class
