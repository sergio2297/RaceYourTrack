package es.sfernandez.raceyourtrack.play.racewayBuilding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import model.raceway.Piece;

public class PiecesCountRecyclerViewAdapter extends RecyclerView.Adapter<PiecesCountRecyclerViewAdapter.ViewHolder> {

    //---- Constants and Definitions ----
    protected static class ViewHolder extends RecyclerView.ViewHolder {

        //---- Attributes ----
        protected final View view;
        protected final Context context;
        protected final ImageView imgPiece;
        protected final TextView txtAmount;
        protected Piece.Type pieceType;

        //---- Constructor ----
        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.context = view.getContext();
            this.imgPiece = view.findViewById(R.id.imgPieceType);
            this.txtAmount = view.findViewById(R.id.txtPieceAmount);
        }
    }

    //---- Attributes ----
    private final Piece.Type[] piecesTypeWithAmount;
    private final int[] piecesCount;

    //---- Constructor ----
    public PiecesCountRecyclerViewAdapter(final int[] piecesCount) {
        this.piecesCount = piecesCount;

        List<Piece.Type> list = new ArrayList<>();
        for(int i = 0; i < piecesCount.length; ++i) {
            if(piecesCount[i] > 0) {
                list.add(Piece.Type.values()[i]);
            }
        }
        this.piecesTypeWithAmount = list.toArray(new Piece.Type[list.size()]);
    }

    //---- Methods ----
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter_pieces_type_count, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pieceType = piecesTypeWithAmount[position];
        holder.imgPiece.setImageDrawable(RaceYourTrackApplication.getContext().getDrawable(holder.pieceType.getDrawableId()));
        holder.txtAmount.setText("x" + piecesCount[holder.pieceType.ordinal()]);
    }

    @Override
    public int getItemCount() {
        return piecesTypeWithAmount.length;
    }

}
