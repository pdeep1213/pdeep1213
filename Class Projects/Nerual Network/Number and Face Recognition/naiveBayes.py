import util
import classificationMethod
import math

class NaiveBayesClassifier(classificationMethod.ClassificationMethod):
  """
  See the project description for the specifications of the Naive Bayes classifier.
  
  Note that the variable 'datum' in this code refers to a counter of features
  (not to a raw samples.Datum).
  """
  def __init__(self, legalLabels):
    self.legalLabels = legalLabels
    self.type = "naivebayes"
    self.k = 1 # this is the smoothing parameter, ** use it in your train method **
    self.automaticTuning = False # Look at this flag to decide whether to choose k automatically ** use this in your train method **
    
  def setSmoothing(self, k):
    """
    This is used by the main method to change the smoothing parameter before training.
    Do not modify this method.
    """
    self.k = k

  def train(self, trainingData, trainingLabels, validationData, validationLabels):
    """
    Outside shell to call your method. Do not modify this method.
    """  
      
    # might be useful in your code later...
    # this is a list of all features in the training set.
    self.features = list(set([ f for datum in trainingData for f in datum.keys() ]))
    
    if (self.automaticTuning):
        kgrid = [0.001, 0.01, 0.05, 0.1, 0.5, 1, 5, 10, 20, 50]
    else:
        kgrid = [self.k]
    
    self.trainAndTune(trainingData, trainingLabels, validationData, validationLabels, kgrid)
      
  def trainAndTune(self, trainingData, trainingLabels, validationData, validationLabels, kgrid):
    """
    Trains the classifier by collecting counts over the training data, and
    stores the Laplace smoothed estimates so that they can be used to classify.
    Evaluate each value of k in kgrid to choose the smoothing parameter 
    that gives the best accuracy on the held-out validationData.
    
    trainingData and validationData are lists of feature Counters.  The corresponding
    label lists contain the correct label for each datum.
    
    To get the list of all possible features or labels, use self.features and 
    self.legalLabels.
    """

    "*** YOUR CODE HERE ***"
    # Initialize data structures
    featureCounts = {label: util.Counter() for label in self.legalLabels}  # Counts for P(feature|label)
    labelCounts = util.Counter()  # Counts for P(label)
    # Count occurrences in training data
    for i, datum in enumerate(trainingData):
        label = trainingLabels[i]
        labelCounts[label] += 1
        for feature, value in datum.items():
            if value > 0:  # Only count "active" features (value > 0)
                featureCounts[label][feature] += 1

        
    # Calculate total number of labels and features
    totalLabels = sum(labelCounts.values())
    numFeatures = len(self.features)
    
    # Store probabilities for the best k
    bestK = None
    bestAccuracy = -1
    bestConditionalProbs = None

    # Evaluate each k in kgrid
    for k in kgrid:
        conditionalProbs = {label: util.Counter() for label in self.legalLabels}
        for label in self.legalLabels:
            for feature in self.features:
                # Apply Laplace smoothing
                numerator = featureCounts[label][feature] + k
                denominator = labelCounts[label] + k * 2  # Binary feature: P(feature=1) and P(feature=0)
                conditionalProbs[label][feature] = numerator / denominator
        
        # Validate the model with current k
        self.conditionalProbs = conditionalProbs

        self.priorProbs = {label: labelCounts[label] / totalLabels for label in self.legalLabels}
        predictions = self.classify(validationData)
        accuracy = sum(int(predictions[i] == validationLabels[i]) for i in range(len(validationLabels))) / len(validationLabels)
        
        # Update the best k
        if accuracy > bestAccuracy:
            bestAccuracy = accuracy
            bestK = k
            bestConditionalProbs = conditionalProbs

    # Store the best model
    self.k = bestK
    self.conditionalProbs = bestConditionalProbs
    self.priorProbs = {label: labelCounts[label] / totalLabels for label in self.legalLabels}
        
  def classify(self, testData):
    """
    Classify the data based on the posterior distribution over labels.
    
    You shouldn't modify this method.
    """
    guesses = []
    self.posteriors = [] # Log posteriors are stored for later data analysis (autograder).
    for datum in testData:
      posterior = self.calculateLogJointProbabilities(datum)
      guesses.append(posterior.argMax())
      self.posteriors.append(posterior)
    return guesses
      
  def calculateLogJointProbabilities(self, datum):
    """
    Returns the log-joint distribution over legal labels and the datum.
    Each log-probability should be stored in the log-joint counter, e.g.    
    logJoint[3] = <Estimate of log( P(Label = 3, datum) )>
    
    To get the list of all possible features or labels, use self.features and 
    self.legalLabels.
    """
    logJoint = util.Counter()  # Stores log-joint probabilities for each label

    for label in self.legalLabels:
        # Start with the log of the prior probability for the label
      
        logJoint[label] = math.log(self.priorProbs[label])
        
        for feature, value in datum.items():
            if value > 0:  # Active feature
                logProb = math.log(self.conditionalProbs[label][feature])
            else:  # Inactive feature
                logProb = math.log(1 - self.conditionalProbs[label][feature])
            
            # Add the feature's contribution to the log-joint probability
            logJoint[label] += logProb

    return logJoint
  
  def findHighOddsFeatures(self, label1, label2):
    """
    Returns the 100 best features for the odds ratio:
        P(feature=1 | label1) / P(feature=1 | label2)
    """
    oddsRatios = []

    for feature in self.features:
        # Compute the odds ratio
        prob_label1 = self.conditionalProbs[label1][feature]
        prob_label2 = self.conditionalProbs[label2][feature]
        
        if prob_label2 > 0:  # Avoid division by zero
            oddsRatio = prob_label1 / prob_label2
        else:
            oddsRatio = float('inf')  # Handle zero probability case gracefully
        
        oddsRatios.append((feature, oddsRatio))

    # Sort features by descending odds ratio and extract the top 100
    oddsRatios.sort(key=lambda x: x[1], reverse=True)
    topFeatures = [feature for feature, _ in oddsRatios[:100]]

    return topFeatures