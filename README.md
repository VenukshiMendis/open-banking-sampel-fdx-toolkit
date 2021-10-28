<!--
 ~  Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com). All Rights Reserved.
 ~   
 ~  This software is the property of WSO2 Inc. and its suppliers, if any. 
 ~  Dissemination of any information or reproduction of any material contained 
 ~  herein is strictly forbidden, unless permitted by WSO2 in accordance with 
 ~  the WSO2 Software License available at https://wso2.com/licenses/eula/3.1. 
 ~  For specific language governing the permissions and limitations under 
 ~  this license, please see the license as well as any agreement you’ve 
 ~  entered into with WSO2 governing the purchase of this software and any 
 ~  associated services.
-->

# WSO2 Open Banking Toolkit Sample

WSO2 Open Banking Toolkit Sample provides a sample Toolkit Implementation

### Building from the source

To build WSO2 Open Banking Sample Toolkit from the source code:

1. Install Java8 or above.
2. Install [Apache Maven 3.0.5](https://maven.apache.org/download.cgi) or above.
3. Install [MySQL](https://dev.mysql.com/doc/refman/5.5/en/windows-installation.html).
4. To get the WSO2 Open Banking Toolkit Sample from [this repository](https://github.com/DivyaPremanantha/open-banking-sample-toolkit), click **Clone or download**.
    * To **clone the solution**, copy the URL and execute the following command in a command prompt.
      `git clone <the copiedURL>`
    * To **download the pack**, click **Download ZIP** and unzip the downloaded file.
5. Navigate to the downloaded solution using a command prompt and run the Maven command.

   |  Command | Description |
      | :--- |:--- |
   | ```mvn install``` | This starts building the pack without cleaning the folders. |
   | ```mvn clean install``` | This cleans the folders and starts building the solution pack from scratch. |
   | ```mvn clean install -P solution``` | This cleans the folders and starts building the solution pack from scratch. Finally creates the toolkit zip files containing the artifacts required to setup the toolkit. |

1. Once the build is created, navigate to the relevant folder to get the toolkit for each product.

   |  Product | Toolkit Path |
      | :--- |:--- |
   | ```Identity Server``` | `/open-banking-sample-toolkit/toolkits/ob-apim/target` |
   | ```API Manager``` | `/open-banking-sample-toolkit/toolkits/ob-is/target` |
   | ```Stream Integrator``` | `/open-banking-sample-toolkit/toolkits/ob-bi/target` |


### Running the products

Please refer the following READ.ME files to run the products.

|  Product | Instructions Path |
| :--- |:--- |
| ```Identity Server``` | `/wso2ob-obiam-toolkit-1.0.0/READ.ME` |
| ```API Manager``` | `/wso2-apim-toolkit-1.0.0/READ.ME` |
| ```Business Intelligence``` | `/wso2ob-bi-toolkit-1.0.0/READ.ME` |


### Reporting Issues

We encourage you to report issues, documentation faults, and feature requests regarding the Open Banking Sample toolkit through the [WSO2 OB Compliance Toolkit Sample Issue Tracker](https://github.com/DivyaPremanantha/open-banking-sample-toolkit/issues).

### License

WSO2 Inc. licenses this source under the WSO2 Software License ([LICENSE](LICENSE)).
