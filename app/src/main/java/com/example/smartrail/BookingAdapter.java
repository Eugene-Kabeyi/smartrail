package com.example.smartrail;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookingList;
    private Context context;

    public BookingAdapter(List<Booking> bookingList, Context context) {
        this.bookingList = bookingList;
        this.context = context;
    }

    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.trainNameTextView.setText(booking.getTrainName());
        holder.dateTextView.setText("Date: " + booking.getDate());
        holder.priceTextView.setText("Price: $" + booking.getPrice());
        holder.totalCostTextView.setText("Total Cost: $" + booking.getTotalCost());

        // Set onClick listener for item to show the receipt
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReceiptActivity.class);
            intent.putExtra("trainName", booking.getTrainName());
            intent.putExtra("departureTime", booking.getDepartureTime());
            intent.putExtra("arrivalTime", booking.getArrivalTime());
            intent.putExtra("date", booking.getDate());
            intent.putExtra("price", booking.getPrice());
            intent.putExtra("passengerName", booking.getPassengerName());
            intent.putExtra("numberOfPassengers", booking.getNumberOfPassengers());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView trainNameTextView;
        TextView dateTextView;
        TextView priceTextView;
        TextView totalCostTextView;

        public BookingViewHolder(View itemView) {
            super(itemView);
            trainNameTextView = itemView.findViewById(R.id.trainNameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            totalCostTextView = itemView.findViewById(R.id.totalCostTextView);
        }
    }
}
