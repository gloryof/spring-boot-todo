import jenkins.model.Jenkins

javaDesc = new hudson.model.JDK.DescriptorImpl();
javaDesc.setInstallations( new hudson.model.JDK("Java", System.getenv() ["JAVA_HOME"]));

mvnDesc = Jenkins.instance.getExtensionList(hudson.tasks.Maven.DescriptorImpl.class)[0];
mvnInstalls = (mvnDesc.installations as List);
mvnInstalls.add(new hudson.tasks.Maven.MavenInstallation("maven3", System.getenv() ["INSTALLE_DIR"], []));
mvnDesc.installations = mvnInstalls;
mvnDesc.save();
