1. Build dfc test driver image

 docker build -f Dockerfile_DFCTest.txt -t 10.8.176.180:5000/kube/dfc/testdriver:16.4.0040.0001 .

2. Push the dfc test driver image

docker push 10.8.176.180:5000/kube/dfc/testdriver:16.4.0040.0001

3. Create database

4. Create docbroker

5. Create CS

6. Create DFC

7. Copy the results

kubectl cp dfc-regr:/opt/dfc_docker/testdriver-tests/results C:/dfc/results/

8. Send mail.

