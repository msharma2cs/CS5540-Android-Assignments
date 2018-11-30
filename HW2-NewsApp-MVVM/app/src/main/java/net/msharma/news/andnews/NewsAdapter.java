package net.msharma.news.andnews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import net.msharma.news.andnews.models.NewsItem;
import net.msharma.news.andnews.utils.DateTimeUtils;
import net.msharma.news.andnews.viewmodels.NewsItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder> {

    private static final String TAG = "NewsAdapter";

    private final LayoutInflater mInflator;
    private List<NewsItem> mNewsItem;
    private NewsItemViewModel mNewsItemViewModel;
    private Context mContext;

    public NewsAdapter(Context context, NewsItemViewModel mNewsItemViewModel){
        this.mContext = context;
        this.mNewsItemViewModel = mNewsItemViewModel;
        this.mInflator = LayoutInflater.from(context);
    }

    public void setmNewsItem(List<NewsItem> newsItem) {
        this.mNewsItem = newsItem;
        notifyDataSetChanged();
    }

    @Override
    public NewsAdapter.NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        boolean shouldAttachToParentImmediately = false;
        View view = mInflator.inflate(R.layout.news_item, parent, shouldAttachToParentImmediately);
        NewsAdapter.NewsItemViewHolder viewHolder = new NewsAdapter.NewsItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if ( mNewsItem != null )
            return mNewsItem.size();
        else return 0;
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;
        TextView description;
        String url;
        TextView publishedAt;
        ImageView articleImg;

        public NewsItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            description = (TextView) itemView.findViewById(R.id.description);
            publishedAt = (TextView) itemView.findViewById(R.id.publishedAt);
            articleImg = (ImageView) itemView.findViewById(R.id.article_img);
        }

        void bind(final int listIndex) {
            title.setText("Title : ".concat(mNewsItem.get(listIndex).getTitle()));
            author.setText("By : ".concat(mNewsItem.get(listIndex).getAuthor()));
            description.setText("Description : ".concat(mNewsItem.get(listIndex).getDescription()));
            publishedAt.setText("Date : ".concat(DateTimeUtils.formatDateFromString(mNewsItem.get(listIndex).getPublishedAt())));
            url = mNewsItem.get(listIndex).getUrl();
            // Image url comes from 'urlToImage' key in json object of api result.
            // Using Picasso library ( http://square.github.io/picasso/ )
            // to load the image from url/web asynchronously into ImageView.
            Picasso
                    .get()
                    .load(mNewsItem.get(listIndex).getUrlToImage())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .fit()
                    .into(articleImg);

            // Open news article in browser on click.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // get the url of clicked article.
                    String urlString = mNewsItem.get(getAdapterPosition()).getUrl();
                    // create intent for browser tab.
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                    // open link in default browser app.
                    mContext.startActivity(browserIntent);
                }
            });
        }
    }

}