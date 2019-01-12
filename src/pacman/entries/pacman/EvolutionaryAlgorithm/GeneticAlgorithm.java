package pacman.entries.pacman.EvolutionaryAlgorithm;

import pacman.Executor;
import pacman.controllers.examples.Legacy;
import pacman.controllers.examples.RandomGhosts;
import pacman.controllers.examples.StarterGhosts;
import pacman.entries.pacman.BTPacMan;
import pacman.entries.pacman.behaviourTree.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm
{
    /**
     * --Constants, you can define here different percentages
     * like mutation, crossover, generation limit, etc.
     */
    static int 			POPULATION_SIZE=100;
    private ArrayList<Genome> 	population = new ArrayList<Genome>();

    /**
     * Use this to store the champion of each generation in case you want
     * to debug or print
     */
    private Genome			champion;
    private Random 			random;

    private Executor exec = new Executor();

    /**
     * Initialize  different needed variables, as well as
     * each individual and added to the population
     */
    public GeneticAlgorithm()
    {
        random = new Random();

        for(int i = 0; i < POPULATION_SIZE; i++)
        {
            Genome individual = new Genome();
            individual.RandomizeChromosome();

            population.add(individual);
        }
    }

    /**
     * Function to test if individual should mutate depending on
     * the mutation rate
     * @param individual individual to test
     * @return true if mutation occurred, false otherwise
     */
    public boolean ShouldMutate(Genome individual)
    {
        int chance = random.nextInt(2);
        return chance == 1;
    }

    /**
     * Creates one or several (usually one or two) offsprings from two parent,
     * this is where you should apply the different sexual operators (one-point crossover,
     * two-point crossover, uniform crossover, etc.) and create the offsprings.
     * You could test right away if it should mutate or not the offspring. You can
     * also have a crossover rate and test if you should apply any sexual operator.
     * @param parent_one
     * @param parent_two
     * @return Offsprings
     */
    public Genome[] Reproduce(Genome parent_one, Genome parent_two)
    {
        //Create and initialize the amount of offsprings you want
        Genome[] offsprings = new Genome[2];
        for(int i = 0; i < 2; i++)
        {
            offsprings[i] = new Genome();
            offsprings[i] = twoPointCrossover(parent_one, parent_two);
        }
        return offsprings;
    }

    /**
     * Function which selects, reproduce, mutate and replace
     * the current population and prepare it to be evaluated.
     */
    private Genome twoPointCrossover(Genome parent_one, Genome parent_two) {
        Genome child = new Genome();

        int crosspoint1 = random.nextInt(Genome.CHROMOSOME_SIZE);
        int crosspoint2 = random.nextInt(Genome.CHROMOSOME_SIZE);

        // Ensure crosspoints are different...
        if (crosspoint1 == crosspoint2){
            if(crosspoint1 == 0){
                crosspoint2++;
            } else {
                crosspoint1--;
            }
        }

        // .. and crosspoint1 is lower than crosspoint2
        if (crosspoint2 < crosspoint1) {
            int temp = crosspoint1;
            crosspoint1 = crosspoint2;
            crosspoint2 = temp;
        }

        for (int i = 0; i < Genome.CHROMOSOME_SIZE; i++) {
            if (i < crosspoint1 || i > crosspoint2)
                child.chromosome[i] = parent_one.chromosome[i];
            else
                child.chromosome[i] = parent_two.chromosome[i];
        }
        return child;
    }

    /**
     * Function which selects, reproduce, mutate and replace
     * the current population and prepare it to be evaluated.
     */
    public void ReproducePopulation()
    {
        //proposed order of functions
        ArrayList<Genome> offsprings = Selection();
        Replacement(offsprings);
    }

    /**
     * Select which individuals are going to reproduce. You should
     * also reproduce the selected parents in this same function and return the
     * offsprings to use them to replace the current population.
     * @return The produced offsprings
     */
    public ArrayList<Genome> Selection()
    {
        ArrayList<Genome> offsprings = new ArrayList<Genome>();

        //implement selection and fill the offsprings

        for (int i = 0 ; i < POPULATION_SIZE/2 ; i+=2){
            Genome parent1 = population.get(i);
            if (ShouldMutate(parent1)) {
                System.out.println("Mutating genome : " + parent1.GetPhenotype());
                parent1.mutate();
                System.out.println("  >Mutating genome :" + parent1.GetPhenotype());
            }
            Genome parent2 = population.get(i+1);
            if (ShouldMutate(parent2)) {
                System.out.println("Mutating genome : " + parent2.GetPhenotype());
                parent2.mutate();
                System.out.println("  >Mutating genome :" + parent2.GetPhenotype());
            }

            Genome[] parents = new Genome[2];
            parents[0] = parent1;
            parents[1] = parent2;
            AddOffsprings(offsprings, parents);

            Genome[] children = Reproduce(parent1, parent2);
            AddOffsprings(offsprings, children);

        }

        return offsprings;

    }

    /**
     * Replace current population with the offsprings. You can use
     * different strategies, as Generational (offsprings for parents) or
     * Elitism, which the fittest parents survive and the rest is replaced
     * by the offsprings, among other strategies.
     * Is always recommended to don't vary the population size.
     */
    public void Replacement(ArrayList<Genome> offsprings)
    {
        this.population = offsprings;
    }

    /**
     * For all members of the population, runs a heuristic that evaluates their fitness
     * based on their phenotype.
     * @return true if the phenotype if filled with only "A", otherwise false
     */
    public boolean EvaluatePopulation()
    {
        int count =0;

        for(Genome individual : population) {

            Node root = pacman.entries.pacman.BTPacMan.BehaviourTree(false,
                                                                    individual.chromosome[0],
                                                                    individual.chromosome[1],
                                                                    individual.chromosome[2],
                                                                    individual.chromosome[3],
                                                                    individual.chromosome[4],
                                                                    individual.chromosome[5],
                                                                    individual.chromosome[6]);
            BTPacMan BTtest = new BTPacMan();
            BTtest.setRoot(root);

            System.out.println("Processing GENOME: " + individual.GetPhenotype());
            ArrayList<Float> fitness = exec.getExperimentValue(BTtest, new StarterGhosts(), 30);
            System.out.println("  - Avg Fitness: " + fitness.get(0) + " - Best Score: " + fitness.get(1) + " - Worst Score: " + fitness.get(2));

            String csvEntry = String.format("%3d, %s, %10.3f, %7.0f, %7.0f", count,individual.GetPhenotype(), fitness.get(0), fitness.get(1),fitness.get(2) );
            DataManager.SaveCSVFile("GeneticDataLog.csv", csvEntry,true);

            individual.SetFitness(fitness);

            count+=1;
        }
        Collections.sort(population);
        return false;
    }

    /**
     * Function that start the evolution process, to be called from the main function.
     */
    public void ExecuteEvolution()
    {
        int current_generation = 0;

        DataManager.SaveCSVFile("GeneticDataLog.csv", "Complete Genetic Evolution Log",false);
        DataManager.SaveCSVFile("GeneticDataLog.csv", "COUNT, GHOST_PROXIMITY_THRESHOLD, HOSTILE_GHOSTS_THRESHOLD, CHASE_THRESHOLD, MIN_DISTANCE, MIN_CLOSE_GHOSTS, GHOSTS_MAX_DISTANCE, AWARENESS_THRESHOLD, Fitness, Best Score, Worst Score ",true);
        DataManager.SaveCSVFile("GeneticDataLog.csv", "GENERATION: " + current_generation,true);
        DataManager.SaveCSVFile("OrderedCompleteGeneticData.csv", "Complete Genetic Evolution Log",false);
        DataManager.SaveCSVFile("OrderedCompleteGeneticData.csv", "COUNT, GHOST_PROXIMITY_THRESHOLD, HOSTILE_GHOSTS_THRESHOLD, CHASE_THRESHOLD, MIN_DISTANCE, MIN_CLOSE_GHOSTS, GHOSTS_MAX_DISTANCE, AWARENESS_THRESHOLD",true);
        DataManager.SaveCSVFile("GeneticDataStatistics.csv", "Genetic Evolution - Generation Statistics",false);
        System.out.println("GENERATION: " + current_generation);

        //You can put anything you want as the stopping condition, Generation limit
        //Found fittest individual, etc.
        while(!EvaluatePopulation() && current_generation<100 )
        {
            DataManager.SaveCSVFile("GeneticDataStatistics.csv", "GENERATION: " + current_generation + " Statistics:",true);
            DataManager.SaveCSVFile("GeneticDataStatistics.csv", GenerateStatistics(true),true);
            DataManager.SaveCSVFile("OrderedCompleteGeneticData.csv", "GENERATION: " + current_generation + " Individuals:",true);
            DataManager.SaveCSVFile("OrderedCompleteGeneticData.csv", printGeneration(),true);
            DataManager.SaveCSVFile("GeneticDataLog.csv", "GENERATION: " + current_generation + " Statistics:",true);
            DataManager.SaveCSVFile("GeneticDataLog.csv", GenerateStatistics(true),true);

            System.out.println("GENERATION: " + current_generation);

            // Create population for next generation
            ReproducePopulation();

            //Keep track of the generation you are in
            current_generation++;

        }

		System.out.println("ENDING GENERATION: " + current_generation);
    }

    /***
     * Helper function to add the recently produced offsprings to the list of created offsprings.
     */
    private String printGeneration() {
        StringBuilder s = new StringBuilder();

        int count =0;
        for (Genome individual: population){
            System.out.println(count + " - " + individual.GetPhenotype() + ", " + individual.GetFitness());

            s.append(String.format(" %4d, %s, %10.3f, %7.0f, %7.0f", count, individual.GetPhenotype() , individual.GetFitness().get(0), individual.GetFitness().get(1),individual.GetFitness().get(2))).append("\n");
//            s.append(count).append(" - ").append(individual.GetPhenotype()).append(", ").append(individual.GetFitness()).append("\n");

            count++;
        }
        return s.toString();
    }

    /***
     * Helper function to add the recently produced offsprings to the list of created offsprings.
     * @param main_offsprings List that stores all the offsprings in one generation
     * @param newly_created_offsprings Newly created offspring from crossover
     */
    private void AddOffsprings(ArrayList<Genome> main_offsprings, Genome[] newly_created_offsprings) {
        for(int i = 0; i < newly_created_offsprings.length; i++) {
            main_offsprings.add(newly_created_offsprings[i]);
        }
    }

    /**
     * Helper function to generate the statistics of the current generation
     * it calculates, worst and best fitness and individual's phenotype,
     * as well as, the average fitness of the population.
     * Use this function if you want to save a .txt or .csv file for
     * using in the report.
     * @param print if true, it will print in screen the results
     */
    public String GenerateStatistics(boolean print)
    {
        float avgFitness=0.f;
        float minFitness=Float.POSITIVE_INFINITY;
        float maxFitness=Float.NEGATIVE_INFINITY;

        Genome bestIndividual = new Genome();
        Genome worstIndividual = new Genome();
        for(int i = 0; i < population.size(); i++){
            float currFitness = population.get(i).GetFitness().get(0);
            avgFitness += currFitness;
            if(currFitness < minFitness){
                minFitness = currFitness;
                worstIndividual = population.get(i);
            }
            if(currFitness > maxFitness){
                maxFitness = currFitness;
                champion = population.get(i);
                bestIndividual = population.get(i);
            }
        }

        avgFitness /= population.size();

        StringBuilder s = new StringBuilder();
        if(print)
        {
            System.out.println("Avg. fitness: " + avgFitness );
            System.out.println("Worst fitness: " + minFitness + "Worst_Best: " + worstIndividual.GetFitness().get(1) + "Worst_Worst: "+ worstIndividual.GetFitness().get(2));
            System.out.println("Best fitness: " + minFitness + "Best_Best: " + bestIndividual.GetFitness().get(1) + "Best_Worst: "+ bestIndividual.GetFitness().get(2));

//            System.out.println("Best individual: " + bestIndividual.GetPhenotype() + "; Worst individual: " + worstIndividual);

        }

        s.append("Avg. fitness: ").append(avgFitness).append("\n");
        s.append("Worst fitness: ").append(minFitness).append(" Worst_Best: ").append(worstIndividual.GetFitness().get(1)).append(" Worst_Worst: ").append(worstIndividual.GetFitness().get(2)).append("\n");
        s.append("Best fitness: ").append(maxFitness).append(" Best_Best: ").append(bestIndividual.GetFitness().get(1)).append(" Best_Worst: ").append(bestIndividual.GetFitness().get(2)).append("\n");

//        s.append("Avg. fitness: ").append(avgFitness).append("; Worst fitness: ").append(minFitness).append("; Best fitness: ").append(maxFitness).append("\n");
//        s.append("Best individual: ").append(bestIndividual).append("; Worst individual: ").append(worstIndividual).append("\n");

        return s.toString();
    }
}
