# parameters
JAVA_CMD = java
MVN_CMD = ./mvnw
MVN_MIGRATE = liquibase
UNAME_S = $(shell uname -s)
OS = what

# java
find-os:
ifeq ($(UNAME_S), Linux)
	@echo "OS name: Linux!"
	$(eval MVN_CMD=./mvnw)
else
	@echo "OS name: Windows!"
	$(eval MVN_CMD=.\mvnw)
endif

java-version:
	$(JAVA_CMD) --version

key-gen:
	@mkcert -install
	@mkcert localhost 127.0.0.1
	@openssl pkcs12 -export -in localhost+1.pem -inkey localhost+1-key.pem -out opwa-keystore.p12 -name opwa-ssl -passout pass:opwa_pwd
	# @mv opwa-keystore.p12 "src/main/resources"
	@rm localhost+1.pem localhost+1-key.pem

# dev
clean: find-os
	$(MVN_CMD) clean

install: find-os
	$(MVN_CMD) dependency:resolve -U

dev: find-os
	$(MVN_CMD) spring-boot:run

# dev-seed: find-os
# 	$(MVN_CMD) spring-boot:run -Dspring-boot.run.arguments="seed_db"

# # migration
# migrate-up: find-os
# 	$(MVN_CMD) $(MVN_MIGRATE):update

# migrate-down: find-os
# 	$(MVN_CMD) $(MVN_MIGRATE):rollback -Dliquibase.rollbackCount=1

# migrate-status: find-os
# 	$(MVN_CMD) $(MVN_MIGRATE):status

.PHONY: clean java-version run migrate-up migrate-down-time migrate-status find-os
