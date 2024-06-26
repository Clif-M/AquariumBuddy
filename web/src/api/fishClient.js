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
export default class FishClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getSingleFish', 'getFish', 'createFish', 'deleteFish', 'updateFish'];
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
     * Gets the fish for the given ID.
     * @param id Unique identifier for a fish
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The fish's metadata.
     */
    async getSingleFish(id, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Encountered token error trying to call Fish endpoint.");
            const response = await this.axiosClient.get(`fish/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.fish;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Get the songs on a given fish by the fish's identifier.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of songs on a fish.
     */
    async getFish(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Encountered token error trying to call Fish endpoint.");
            const response = await this.axiosClient.get(`fish`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.fish;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Create a new fish owned by the current user.
     * @param name The name of the fish to create.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The fish that has been created.
     */
    async createFish(name, imageUrl, species, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create fish.");
            const response = await this.axiosClient.post(`fish`, {
                fish: {
                    name: name,
                    imageUrl: imageUrl,
                    species: species
                }
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.fish;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Updates a fish owned by the current user.
     * @param name The name of the fish to update.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The fish that has been created.
     */
    async updateFish(fish, errorCallback) {
        //TODO ADD all fields for update
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create fish.");
            const response = await this.axiosClient.put(`fish`, {
                fish: fish
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.fish;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Delete a fish owned by the current user.
     * @param name The name of the fish to create.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The fish that has been created.
     */
    async deleteFish(fishId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can delete fish.");
            const response = await this.axiosClient.delete(`fish/${fishId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.fish;
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
