# Cloud Deployment
----

**Logging into Brainstorm space**

cf login --skip-ssl-validation -a api.cf-np.threega.com -u $user -p $password -o $org -s $space

**Pushing the App**

cf push project-setup -p target/universal/*.zip -t 180 -m 1024M -i 1 --no-route --no-start

**Mapping the Route**

cf map-route project-setup cf-np.local -n project-setup
cf unmap-route project-setup-temp cf-np.local -n project-setup-temp

**Creating Required Services**

cf cups Ping -p '{
 "alias":"ping",
 "url" : "**************"
}'

**Prod Bindings**

cf cups Ping -p '{
 "alias":"ping",
 "url" : "************"
}'

**Binding Required Services**

cf bs project-setup Ping

**Starting the App**

cf start project-setup

**Restarting the App**

cf restage project-setup


