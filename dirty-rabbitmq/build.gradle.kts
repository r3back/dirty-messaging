dependencies {
    implementation("com.rabbitmq:amqp-client:5.16.0")
    implementation("org.msgpack:msgpack-core:0.9.5")
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation(project(":dirty-api"))

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
}