package rbm;

import java.io.Serializable;

public class InputRBM extends SimpleRBM implements Serializable{

	private static final long serialVersionUID = 5035486678274163394L;

    public InputRBM(int numVisibleNodes, int numHiddenNodes)
    {
        super(numVisibleNodes, numHiddenNodes);
    }

    /**
     * For each group, probabilistically chooses one bit from that group to
     * activate based on the probability distribution defined by each bit's
     * probability of activation. Once one bit is chosen, all other bits in the
     * group are set to 0.
     *
     * @param probabilities - the array of probabilities that define a distribution
     *
     * Helper method to normalize the probabilities and create a distribution for
     * the activateVisibleProbDist function.
     */
	private static void normalizeProbabilities(float[] probabilities)
    {
        float sum = 0;

        for (int i = 0; i < probabilities.length; i++)
        {
            sum += probabilities[i];
        }

        //Normalize probabilities by dividing by sum
        for (int i = 0; i < probabilities.length; i++)
        {
            probabilities[i] = probabilities[i]/sum;
        }

        float lastValue = 0;

        //Create distribution - distance between each value in the array will
        //correspond to that bit's likelihood of being picked.
        for (int i = 0; i < probabilities.length; i++)
        {
            probabilities[i] += lastValue;
            lastValue = probabilities[i];
        }
    }

    /**
     * @param newInput - the bit array to set as the visible layer
     */
    public void setInput(boolean[] newInput)
    {
        for (int i=0; i < newInput.length; i++)
        {
            this.visibleNodes[i] = newInput[i];
        }

    }

    /**
     * @return - all the activation probabilities for the visible nodes,
     * organized into a 2D array
     */
    public float[] predict()
    {
        float[] probabilities = new float[this.visibleNodes.length];

        for (int i = 0; i < probabilities.length; i++)
        {
            probabilities[i] = logSigmoid(computeVisibleWeightedSum(i), 1);
        }

        return probabilities;
    }
}
