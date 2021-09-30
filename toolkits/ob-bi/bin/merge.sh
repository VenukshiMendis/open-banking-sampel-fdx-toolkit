#!/bin/bash
# ------------------------------------------------------------------------
#
# Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com). All Rights Reserved.
#
# This software is the property of WSO2 Inc. and its suppliers, if any.
# Dissemination of any information or reproduction of any material contained
# herein is strictly forbidden, unless permitted by WSO2 in accordance with
# the WSO2 Software License available at https://wso2.com/licenses/eula/3.1.
# For specific language governing the permissions and limitations under this
# license, please see the license as well as any agreement you’ve entered into
# with WSO2 governing the purchase of this software and any associated services.
# ------------------------------------------------------------------------

# merge.sh script copy the WSO2 OB BI Sample Toolkit artifacts on top of WSO2 SI base product
#
# merge.sh <WSO2_OB_BI_HOME>

WSO2_OB_BI_HOME=$1

# set accelerator home
cd ../
ACCELERATOR_HOME=$(pwd)
echo "Accelerator home is: ${ACCELERATOR_HOME}"

# set product home
if [ "${WSO2_OB_BI_HOME}" == "" ];
  then
    cd ../
    WSO2_OB_BI_HOME=$(pwd)
    echo "Product home is: ${WSO2_OB_BI_HOME}"
fi

# validate product home
if [ ! -d "${WSO2_OB_BI_HOME}/deployment/siddhi-files" ]; then
  echo -e "\n\aERROR:specified product path is not a valid carbon product path\n";
  exit 2;
else
  echo -e "\nValid carbon product path.\n";
fi

echo -e "\nCopying open banking artifacts\n"
echo -e "================================================\n"
cp -r ${ACCELERATOR_HOME}/carbon-home/* "${WSO2_OB_BI_HOME}"/
echo -e "\nComplete!\n"
