import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the AquariumBuddy API.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class LogClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getLog', 'getLogsForTank', 'createLog', 'deleteLog', 'updateLog', 'getLogsByType'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Gets the Logs for the given TankID.
     * @param id Unique identifier for a tank
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The tank's metadata.
     */
    async getLog(logId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Encountered token error trying to call Tank endpoint.");
            const response = await this.axiosClient.get(`logs/${logId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.log;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

     /**
     * Get the songs on a given tank by the tank's identifier.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of songs on a tank.
     */
     async getLogsByType(tankId, flavor, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Encountered token error trying to call log endpoint.");
            const response = await this.axiosClient.get(`logs/tank/${tankId}/flavor/${flavor}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            const sortedResponse = response.data.log.sort(function (a, b) {
                return a.logDate.localeCompare(b.logDate);
            });;
            return sortedResponse.reverse();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Get the songs on a given tank by the tank's identifier.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of songs on a tank.
     */
    async getLogsForTank(tankId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Encountered token error trying to call log endpoint.");
            const response = await this.axiosClient.get(`logs/tank/${tankId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            const sortedResponse = response.data.log.sort(function (a, b) {
                return a.logDate.localeCompare(b.logDate);
            });;
            return sortedResponse.reverse();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Create a new tank owned by the current user.
     * @param name The name of the tank to create.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The tank that has been created.
     */
    async createLog(flavor, tankId, notes, logDate,  errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create tanks.");
            const response = await this.axiosClient.post(`logs`, {
                log:{
                flavor: flavor,
                notes: notes,
                tankId: tankId,
                logDate: logDate
                }
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.log;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Updates a tank owned by the current user.
     * @param name The name of the tank to update.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The tank that has been created.
     */
    async updateLog(log, errorCallback) {
        //TODO ADD all fields for update
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update Logs.");
            const response = await this.axiosClient.put(`logs`, {
                log: log
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.log;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Delete a tank owned by the current user.
     * @param name The name of the tank to create.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The tank that has been created.
     */
    async deleteLog(logId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can delete tanks.");
            const response = await this.axiosClient.delete(`logs/${logId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.log;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }


    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
