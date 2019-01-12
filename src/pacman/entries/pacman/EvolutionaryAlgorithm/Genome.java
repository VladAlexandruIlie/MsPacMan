package pacman.entries.pacman.EvolutionaryAlgorithm;

import java.util.ArrayList;
import java.util.Random;

public class Genome implements Comparable<Genome>{

    // --- constants
    static int CHROMOSOME_SIZE=7;

    public int[] chromosome;
    private Random random;



    protected ArrayList<Float> fitness;
    public ArrayList<Float> GetFitness(){ return this.fitness; }
    public void SetFitness(ArrayList<Float> value){ this.fitness = value; }

    /***
     * Initialize needed variables
     */
    public Genome()
    {
        fitness = new ArrayList<>();
        random = new Random();
        chromosome = new int[CHROMOSOME_SIZE];
    }

    /**
     * Initialize each chromosome with a random value between 0 and 1  (remember random is [inclusive] - [exclusive])
     * Meaning that it will never reach the max value (random.nextInt(11) --> numbers from 0 to 10)
     */
    public void RandomizeChromosome() {
        chromosome[0] = random.nextInt(100);    //        GHOST_PROXIMITY_THRESHOLD
        chromosome[1] = random.nextInt(5);      //        HOSTILE_GHOSTS_THRESHOLD
        chromosome[2] = random.nextInt(100);    //        CHASE_THRESHOLD
        chromosome[3] = random.nextInt(100);    //        MIN_DISTANCE
        chromosome[4] = random.nextInt(5);      //        MIN_CLOSE_GHOSTS
        chromosome[5] = random.nextInt(100);    //        GHOSTS_MAX_DISTANCE
        chromosome[6] = random.nextInt(200);    //        AWARENESS_THRESHOLD
    }

    /**
     * Mutates a genome using inversion, random mutation or other methods.
     * This function should be called after the mutation chance is rolled.
     * Mutation can occur to the parent before reproduction, the offspring once it is created
     * to a genome which was not chose for reproduction or the whole population once
     * sexual operators and replacement have been done.
     */
    public void mutate()
    {
        int chance = random.nextInt(3) + 1 ;
        for (int i=0; i< chance; i+=1){

            int chance2 = random.nextInt(7);

            if (chance2 == 0){
                int chance3 = random.nextInt(2);
                if (chance3 == 1) { chromosome[chance2] = (chromosome[chance2]+ 3) % 100; }
                else{ if (chromosome[chance2]>2) chromosome[chance2] = (chromosome[chance2] - 3) ; }
            }
            else if (chance2 == 1){
                int chance3 = random.nextInt(2);
                if (chance3 == 1) { chromosome[chance2] = (chromosome[chance2]+ 1) % 5; }
                else{ if (chromosome[chance2]>0) chromosome[chance2] = (chromosome[chance2] - 1) ; }
            }
            else if (chance2 == 2){
                int chance3 = random.nextInt(2);
                if (chance3 == 1) { chromosome[chance2] = (chromosome[chance2]+ 2) % 100; }
                else{ if (chromosome[chance2]>1) chromosome[chance2] = (chromosome[chance2] - 2) ; }
            }
            else if (chance2 == 3){
                int chance3 = random.nextInt(2);
                if (chance3 == 1) { chromosome[chance2] = (chromosome[chance2]+ 2) % 100; }
                else{ if (chromosome[chance2]>1) chromosome[chance2] = (chromosome[chance2] - 2) ; }
            }
            else if (chance2 == 4){
                int chance3 = random.nextInt(2);
                if (chance3 == 1) { chromosome[chance2] = (chromosome[chance2]+ 1) % 5; }
                else{ if (chromosome[chance2]>0) chromosome[chance2] = (chromosome[chance2] - 1) ; }
            }
            else if (chance2 == 5){
                int chance3 = random.nextInt(2);
                if (chance3 == 1) { chromosome[chance2] = (chromosome[chance2]+ 2) % 100; }
                else{ if (chromosome[chance2]>1) chromosome[chance2] = (chromosome[chance2] - 2) ; }
            }
            else if (chance2 == 6){
                int chance3 = random.nextInt(2);
                if (chance3 == 1) { chromosome[chance2] = (chromosome[chance2] + 5) % 200; }
                else{ if (chromosome[chance2]>4) chromosome[chance2] = (chromosome[chance2] - 5) ; }
            }
        }
    }

    /**
     * Corresponds the chromosome encoding to the phenotype, which is a representation
     * that can be read, tested and evaluated by the main program.
     * @return a String with a length equal to the chromosome size, composed of A's
     * at the positions where the chromosome is 1 and a's at the posiitons
     * where the chromosme is 0
     */
    public String GetPhenotype()
    {
        String phenotype = String.format(" %3d, %3d, %3d, %3d, %3d, %3d, %3d",
                chromosome[0],chromosome[1],chromosome[2],chromosome[3],chromosome[4],chromosome[5],chromosome[6] );
        return phenotype;
    }

    /**
     * Helper Function to compare different genomes based on fitness
     * to for example sort the population using Collections.Sort(<ArrayList of genomes>).
     */
    @Override
    public int compareTo(Genome arg0)
    {
        if(GetFitness().get(0) == arg0.GetFitness().get(0) ) { return 0; }
        else if(GetFitness().get(0) > arg0.GetFitness().get(0) ) { return -1; }
        else { return 1; }
    }

}
