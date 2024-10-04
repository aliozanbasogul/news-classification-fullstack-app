import axios from 'axios';

//const API_BASE_URL = "http://54.146.212.23:8080/api/v1";  
const API_BASE_URL = "http://localhost:8080/api/v1";  
export const getNews = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/news`);
        console.log("News data:", response.data);
        return response.data;
    } catch (error) {
        console.error("Error fetching news:", error);
        throw error;
    }
};
