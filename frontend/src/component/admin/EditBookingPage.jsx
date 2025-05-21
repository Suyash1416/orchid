import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

const EditBookingPage = () => {
    const navigate = useNavigate();
    const { bookingCode } = useParams();
    const [bookingDetails, setBookingDetails] = useState(null);
    const [error, setError] = useState(null);
    const [success, setSuccessMessage] = useState(null);
    const [roomTypes, setRoomTypes] = useState([]);
    const [selectedRoomType, setSelectedRoomType] = useState('');
    const [loading, setLoading] = useState(true); // 👈 New loading state

    useEffect(() => {
        const fetchBookingDetails = async () => {
            try {
                const response = await ApiService.getBookingByConfirmationCode(bookingCode);
                setBookingDetails(response.booking);
                setSelectedRoomType(response.booking?.room?.roomType || '');
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false); // ✅ Stop loading after both calls
            }
        };

        const fetchRoomTypes = async () => {
            try {
                const response = await ApiService.getRoomTypes();
                setRoomTypes(response.roomTypes || []);
            } catch (error) {
                setError(error.message);
            }
        };

        fetchBookingDetails();
        fetchRoomTypes();
    }, [bookingCode]);

    const handleRoomTypeChange = (event) => {
        setSelectedRoomType(event.target.value);
    };

    const acheiveBooking = async (bookingId) => {
        if (!window.confirm('Are you sure you want to Acheive this booking?')) {
            return;
        }

        try {
            const response = await ApiService.cancelBooking(bookingId);
            if (response.statusCode === 200) {
                setSuccessMessage("The booking was Successfully Acheived");
                setTimeout(() => {
                    setSuccessMessage('');
                    navigate('/admin/manage-bookings');
                }, 3000);
            }
        } catch (error) {
            setError(error.response?.data?.message || error.message);
            setTimeout(() => setError(''), 5000);
        }
    };

    return (
        <div className="find-booking-page">
            <h2>Booking Detail</h2>

            {loading ? ( // 👈 Show loading message if still loading
                <p>Loading booking data...</p>
            ) : (
                <>
                    {error && <p className='error-message'>{error}</p>}
                    {success && <p className='success-message'>{success}</p>}
                    {bookingDetails && (
                        <div className="booking-details">
                            <h3>Booking Details</h3>
                            <p>Confirmation Code: {bookingDetails.bookingConfirmationCode}</p>
                            <p>Check-in Date: {bookingDetails.checkInDate}</p>
                            <p>Check-out Date: {bookingDetails.checkOutDate}</p>
                            <p>Num Of Adults: {bookingDetails.numOfAdults}</p>
                            <p>Num Of Children: {bookingDetails.numOfChildren}</p>
                            <p>Guest Email: {bookingDetails.guestEmail}</p>

                            <br />
                            <hr />
                            <br />
                            <h3>Booker Details</h3>
                            <div>
                                <p>Name: {bookingDetails.user?.name}</p>
                                <p>Email: {bookingDetails.user?.email}</p>
                                <p>Phone Number: {bookingDetails.user?.phoneNumber}</p>
                            </div>

                            <br />
                            <hr />
                            <br />
                            <h3>Room Details</h3>
                            <div>
                                <p>Room Type:
                                    <select value={selectedRoomType} onChange={handleRoomTypeChange}>
                                        {(roomTypes || []).map((roomType) => (
                                            <option key={roomType} value={roomType}>
                                                {roomType}
                                            </option>
                                        ))}
                                    </select>
                                </p>
                                <p>Room Price: ${bookingDetails.room?.roomPrice}</p>
                                <p>Room Description: {bookingDetails.room?.roomDescription}</p>
                                {bookingDetails.room?.roomPhotoUrl && (
                                    <img src={bookingDetails.room.roomPhotoUrl} alt="Room" />
                                )}
                            </div>
                            <button
                                className="acheive-booking"
                                onClick={() => acheiveBooking(bookingDetails.id)}>Acheive Booking
                            </button>
                        </div>
                    )}
                </>
            )}
        </div>
    );
};

export default EditBookingPage;
