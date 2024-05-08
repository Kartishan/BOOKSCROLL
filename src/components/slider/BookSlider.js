import React, { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./BookSlider.css"
import {API_URL} from "../../config";

const BookSlider = ({ ...props }) => {
    const [data, setData] = useState([]);
    const [currentIndex, setCurrentIndex] = useState(0);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get(`${API_URL}api/book/category/Роман`)
            .then(response => setData(response.data.content))
            .catch(error => console.error('Ошибка при загрузке данных:', error));
    }, []);

    const handlePrev = () => {
        setCurrentIndex(prevIndex => (prevIndex > 0 ? prevIndex - 1 : 0));
    };

    const handleClick = (bookId) => {
        navigate(`/book/${bookId}`);
    };
    const handleAllBooks = () => {
        navigate('/books-by-category', { state: { category: 'Роман' } }); // Название категории, например "Роман"
    };
    const handleNext = () => {
        setCurrentIndex(prevIndex => (prevIndex < data.length - 6 ? prevIndex + 1 : prevIndex));
    };

    return (
        <div className="SlaiderContainer">
            <div className="categoryHeader">
                <h2 className="categoryName">Роман</h2>
                <p className="allButton" onClick={handleAllBooks}>Все ></p>
            </div>
            <div className="slider">
                <div className="categoryContent">
                    <button className="sliderButton" onClick={handlePrev}>&lt;</button>
                    {data.slice(currentIndex, currentIndex + 6).map(item => (
                        <div className="content" key={item.id} onClick={() => handleClick(item.id)}>
                            <img src={`http://localhost:8080/api/image/${item.id}`} className="categoryImage"
                                 alt={item.name}/>
                            <p className="categoryBookName">{item.name}</p>
                            <p className="categoryBookAuthor">{item.author}</p>
                        </div>
                    ))}
                    <button className="sliderButton" onClick={handleNext}>&gt;</button>
                </div>
            </div>
        </div>
    );
};

export default BookSlider;
