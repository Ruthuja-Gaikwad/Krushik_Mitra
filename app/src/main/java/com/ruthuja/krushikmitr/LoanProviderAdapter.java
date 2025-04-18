package com.ruthuja.krushikmitr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LoanProviderAdapter extends RecyclerView.Adapter<LoanProviderAdapter.ViewHolder> {

    private final List<LoanProvider> loanProviders;

    public LoanProviderAdapter(List<LoanProvider> loanProviders) {
        this.loanProviders = loanProviders != null ? loanProviders : List.of(); // Avoid NullPointerException
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loan_provider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoanProvider provider = loanProviders.get(position);

        // Use String.format for cleaner string concatenation
        holder.tvName.setText(provider.getName());
        holder.tvInterestRate.setText(String.format("Interest Rate: %s", provider.getInterestRate()));
        holder.tvLoanAmount.setText(String.format("Loan Amount: %s", provider.getLoanAmountRange()));
        holder.tvContactInfo.setText(String.format("Contact: %s", provider.getContactInfo()));

        // Improve accessibility (TalkBack support)
        holder.itemView.setContentDescription(String.format(
                "Loan Provider: %s, Interest Rate: %s, Loan Amount Range: %s, Contact: %s",
                provider.getName(), provider.getInterestRate(), provider.getLoanAmountRange(), provider.getContactInfo()
        ));
    }

    @Override
    public int getItemCount() {
        return loanProviders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvInterestRate, tvLoanAmount, tvContactInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_provider_name);
            tvInterestRate = itemView.findViewById(R.id.tv_interest_rate);
            tvLoanAmount = itemView.findViewById(R.id.tv_loan_amount);
            tvContactInfo = itemView.findViewById(R.id.tv_contact_info);
        }
    }
}
