/**
 *  ComEd Price Automation
 *
 *  Copyright 2021 CHARLIE HAYES
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "ComEd Price Automation",
    namespace: "idontusenumbers",
    author: "CHARLIE HAYES",
    description: "Use ComEd hourly pricing to control devices",
    category: "Green Living",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")

def api = ""
preferences {

    section("High price threshold:") {
        input "highprice", "decimal", required: true, title: "Cents per kWh"
    }
    
    section("On when price is high") {
        input "highswitch", "capability.switch", required: false
    }
    section("On when price is normal") {
        input "normalswitch", "capability.switch", required: false
    }
    section("On when price is negative") {
        input "negative", "capability.switch", required: false
    }
    
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
    
    
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    
    runEvery10Minutes(priceUpdated)
    priceUpdated()
}


def priceUpdated(data) {
    log.debug "handlerMethod2, data: $data"
    
    
    def params = [
        uri: "https://hourlypricing.comed.com",
        path: "/api",
        query: [type:'currenthouraverage']
	]

    try {
        httpGet(params) { resp ->
            resp.headers.each {
            log.debug "${it.name} : ${it.value}"
        }
        log.debug "response contentType: ${resp.contentType}"
        log.debug "response data: ${resp.data}"
        
        
        }
    } catch (e) {
        log.error "something went wrong: $e"
    }


}


// TODO: implement event handlers