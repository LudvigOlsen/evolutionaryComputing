
# Population Divergence Metric Test

library(tidyverse)
library(knitr)

setwd("/Users/ludvigolsen/Documents/UVA/Artificial Intelligence 2018 - 20/Evolutionary Computing/evolutionaryComputing/population_divergence_metric/")

deduplicator_indices <- function(df){
  dt <- data.table::data.table(df)
  return(as_data_frame(dt[dt[, .I[1], by = list(pmin(population_1, population_2), pmax(population_1, population_2))]$V1]))
}

#### Experiment 1
# Whether adding the same bias to all genes in the population can be detected by the divergence metric

# Read data
divergence_simulation_data_exp1 <- read.csv("population_divergence_sample_experiment_1.csv", sep=",")

# Deduplicate comparisons (i.e., A,B = B,A)
distinct_comparisons_exp1 <- deduplicator_indices(divergence_simulation_data_exp1 %>% select(population_1, population_2))

# Filter out duplicates and calculate bias difference between populations
divergence_simulation_data_exp1_processed <- divergence_simulation_data_exp1 %>%
  right_join(distinct_comparisons_exp1) %>%
  mutate(bias_difference = bias_1-bias_2) %>%
  select(-X)

# Plot
tiff("Experiment_One_Divergence_of_differently_biased_populations_small.tiff", units="in", width=5, height=4, res=300)

divergence_simulation_data_exp1_processed %>%
  ggplot(aes(bias_difference, divergence)) +
    geom_smooth() +
    labs(x="Bias Difference", y="Divergence", title="Divergence of two differently biased populations") +
    theme_bw()

dev.off()

#### Experiment 2


# Read data
divergence_simulation_data_exp2 <- read.csv("population_divergence_sample_experiment_2.csv", sep=",")

# Filter out duplicates and calculate bias difference between populations
divergence_simulation_data_exp2_processed <- divergence_simulation_data_exp2 %>%
  select(-X)

# Plot

tiff("Experiment_two_pairwise_comparison_small.tiff", units="in", width=5, height=4, res=300)

divergence_simulation_data_exp2_processed %>%
  ggplot(aes(bias_1_first, divergence)) +
  geom_smooth() +
  labs(x="Bias", y="Divergence", title="Comparing pairs with ((-bias, bias), (bias, -bias))") +
  theme_bw()

dev.off()




# \nFirst 5 genes for population 1 are -bias, last 5 are +bias\nFirst 5 genes for population 2 are +bias, last 5 are -bias
