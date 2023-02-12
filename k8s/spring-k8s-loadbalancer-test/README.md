Pre-reqs
========
- A Docker daemon running
- `kind` installed (e.g `brew install kind`)

Setup Details
=============
- sets up a Kind Kubernetes test cluster
    - configs it to audit requests to the Kubernetes API Server: get, watch and describe Services and Endpoints
- install test apps
- sets up a port-forward to the `service-one`
- make 10 curl requests to `service-one` that will result in a call to `service-two`
    - log the curl request-response and the kubernetes api-requests defined above


To install
==========
Note; this will delete the default `kind` container

Run `./install.sh`

To run the test
===============
Run `./test.sh`