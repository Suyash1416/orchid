import React from "react";
import { FaFacebook, FaInstagram, FaYoutube, FaLinkedin } from "react-icons/fa"; // Import icons
import { MdLocationOn } from "react-icons/md"; // Google Maps icon

const FooterComponent = () => {
  return (
    <footer className="footer">
      <span className="my-footer">
        -- By Suyash Sable --  
        <br />  
        Orchid Paying Guest | All Rights Reserved &copy; {new Date().getFullYear()}
      </span>
      <div className="social-links">
        <a href="https://www.instagram.com/suyashsable_14/" target="_blank" rel="noopener noreferrer">
          <FaInstagram size={24} className="social-icon" />
        </a>
        <a href="https://www.facebook.com/yourprofile" target="_blank" rel="noopener noreferrer">
          <FaFacebook size={24} className="social-icon" />
        </a>
        <a href="https://www.youtube.com/yourchannel" target="_blank" rel="noopener noreferrer">
          <FaYoutube size={24} className="social-icon youtube" />
        </a>
        <a href="https://in.linkedin.com/in/suyash-sable-3240a4282" target="_blank" rel="noopener noreferrer">
          <FaLinkedin size={24} className="social-icon linkedin" />
        </a>
        <a href="https://www.google.com/maps/place//@18.5577586,73.9421265,142m/data=!3m1!1e3!4m9!1m8!3m7!1s0x3bc2c3c3288ba495:0x38e833613a63004a!2sKharadi,+Pune,+Maharashtra!3b1!8m2!3d18.5538241!4d73.9476689!16s%2Fm%2F0hhss3k?entry=ttu&g_ep=EgoyMDI1MDMxMi4wIKXMDSoASAFQAw%3D%3D" target="_blank" rel="noopener noreferrer">
          <MdLocationOn size={24} className="social-icon map" />
        </a>
      </div>
    </footer>
  );
};

export default FooterComponent;
