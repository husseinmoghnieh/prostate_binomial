library(h2o)
h2o.init()

h2o_df  <- h2o.importFile(path = "data/prostate.csv")

h2o_df$CAPSULE = as.factor(h2o_df$CAPSULE)
rand_vec <- h2o.runif(h2o_df, seed = 1234)

train <- h2o_df[rand_vec <= 0.8,]
valid <- h2o_df[(rand_vec > 0.8),]
binomial.fit = h2o.glm(y = "CAPSULE", x = c("AGE", "RACE", "PSA", "GLEASON"), training_frame = train, validation_frame = valid, family ="binomial", model_id = "ProstateModel")

# Make and export predictions.
h2o_single_test_1  <- h2o.importFile(path = "data/prostate_test_actual0.csv")
h2o_single_test_2  <- h2o.importFile(path = "data/prostate_test_actual1.csv")

pred_test_1 = h2o.predict(binomial.fit, h2o_single_test_1)
pred_test_2 = h2o.predict(binomial.fit, h2o_single_test_2)

if (! file.exists("tmp")) {
  dir.create("tmp")
}

h2o.exportFile(pred_test_1, "tmp/pred_test_1.csv", force = TRUE)
h2o.exportFile(pred_test_2, "tmp/pred_test_2.csv", force = TRUE)
h2o.download_pojo(binomial.fit , path = "tmp")

# Or you can export the predictions to hdfs:
#   h2o.exportFile(pred, "hdfs://namenode/path/to/file.csv")1718

# Calculate metrics.
#perf = h2o.performance(binomial.fit, test)
#print(perf)