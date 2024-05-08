import React from 'react';
import "./Home.css"
import TryScroll from "../../components/tryScroll/TryScroll";
import MyHeader from "../../components/header/MyHeader";
import MyFooter from "../../components/footer/MyFooter";
import BookSlider from "../../components/slider/BookSlider";

const Home = () => {
    return (
        <div className="wrapper">
            <header>
                <MyHeader/>
            </header>
            <main>
                <TryScroll/>
                <BookSlider/>
            </main>
            <footer>
                <MyFooter/>
            </footer>
        </div>
    );
};

export default Home;
